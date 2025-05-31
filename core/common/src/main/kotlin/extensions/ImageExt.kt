package extensions

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.toPixelMap
import androidx.core.content.FileProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

fun ImageBitmap.toAndroidBitmap(): Bitmap {
    // 1. Compose의 PixelMap 가져오기
    val pixelMap = this.toPixelMap()

    // 2. PixelMap 크기에 맞는 빈 Bitmap 생성
    val bitmap = Bitmap.createBitmap(pixelMap.width, pixelMap.height, Bitmap.Config.ARGB_8888)

    // 3. PixelMap을 순회하며 Bitmap에 픽셀 쓰기
    for (y in 0 until pixelMap.height) {
        for (x in 0 until pixelMap.width) {
            val color: Color = pixelMap[x, y]
            bitmap.setPixel(x, y, color.toArgb())
        }
    }
    return bitmap
}

fun Bitmap.saveBitmapToFile(
    context: Context,
    fileName: String,
    onSuccess: (Uri, String) -> Unit,
    onFailure: () -> Unit
) {
    val filename = "$fileName.png"

    // MediaStore에 저장할 파일 정보를 설정
    val contentValues = ContentValues().apply {
        put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
        put(MediaStore.MediaColumns.MIME_TYPE, "image/png")
        put(MediaStore.MediaColumns.RELATIVE_PATH, "${Environment.DIRECTORY_PICTURES}/wepli")
    }

    // ContentResolver를 통해 이미지 저장 Uri 생성
    val resolver = context.contentResolver
    val uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

    if (uri == null) {
        onFailure()
        return
    }

    // 생성된 Uri에 출력 스트림을 열어 Bitmap을 저장
    resolver.openOutputStream(uri)?.use { outputStream ->
        val success = this@saveBitmapToFile.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        val absolutePath =
            "${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)}/wepli/$filename"

        if (success) onSuccess(uri, absolutePath) else onFailure()
    } ?: {
        onFailure()
    }
}

fun Bitmap.saveBitmapToCache(
    context: Context,
    fileName: String,
    onSuccess: (Uri, String) -> Unit,
    onFailure: () -> Unit
) {
    val filename = "$fileName.png"
    val file = File(context.cacheDir, filename)

    try {
        FileOutputStream(file).use { outputStream ->
            val success = this.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            if (success) {
                val uri = FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", file)
                onSuccess(uri, file.absolutePath)
            } else {
                onFailure()
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
        onFailure()
    }
}

fun Bitmap.saveBitmapToCache(
    context: Context,
    fileName: String = "wepli_${System.currentTimeMillis()}"
): Uri? {
    val filename = "$fileName.png"
    val file = File(context.cacheDir, filename)

    return runCatching {
        FileOutputStream(file).use { outputStream ->
            val success = this.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            if (success) {
                FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", file)
            } else {
                null
            }
        }
    }.getOrNull()
}

suspend fun ByteArray.compressImage(
    maxSizeMB: Int = 5,
    initialQuality: Int = 100,
    minQuality: Int = 10,
): ByteArray = withContext(Dispatchers.Default) {
    val bitmap = BitmapFactory.decodeByteArray(this@compressImage, 0, size)
    val maxSizeInBytes = maxSizeMB * 1024 * 1024
    var quality = initialQuality
    var compressed: ByteArray

    do {
        ByteArrayOutputStream().use { stream ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, stream)
            compressed = stream.toByteArray()
            quality -= 10
        }
    } while (compressed.size > maxSizeInBytes && quality >= minQuality)

    compressed
}