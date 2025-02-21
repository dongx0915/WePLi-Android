package extensions

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.os.Environment
import android.provider.MediaStore

fun Bitmap.saveBitmapToFile(
    context: Context,
    fileName: String,
    onSuccess: () -> Unit,
    onFailure: () -> Unit
) {
    val filename = "$fileName.png"

    // MediaStore에 저장할 파일 정보를 설정
    val contentValues = ContentValues().apply {
        put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
        put(MediaStore.MediaColumns.MIME_TYPE, "image/png")
        put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
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
        if (success) onSuccess() else onFailure()
    } ?: {
        onFailure()
    }
}