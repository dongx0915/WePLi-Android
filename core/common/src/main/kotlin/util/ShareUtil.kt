package util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.widget.Toast
import com.wepli.core.common.R
import java.io.File


object ShareUtil {
    private const val IMAGE_TYPE: String = "image/*"
    private const val FILENAME: String = "/myPhoto.jpg"
    private val mediaPath: String = Environment.getExternalStorageDirectory().toString() + FILENAME

    private const val INSTAGRAM_PACKAGE_NAME = "com.instagram.android"

    private fun createInstagramIntent(context: Context) {
        val media = File(mediaPath)
        val uri = Uri.fromFile(media)
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            this.type = type
            putExtra(Intent.EXTRA_STREAM, uri)
        }

        context.startActivity(Intent.createChooser(shareIntent, "Share to"))
    }

    fun shareToInstagramStory(
        activity: Activity,
        backgroundAssetUri: Uri,
        stickerAssetUri: Uri,
        facebookAppId: String
    ) {
        val intent = Intent("com.instagram.share.ADD_TO_STORY").apply {
            setDataAndType(backgroundAssetUri, "image/jpeg")
            putExtra("source_application", facebookAppId)
            putExtra("interactive_asset_uri", stickerAssetUri)
            flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        }

        // 스티커에 대한 권한 부여
        activity.grantUriPermission(
            "com.instagram.android",
            stickerAssetUri,
            Intent.FLAG_GRANT_READ_URI_PERMISSION
        )

        // 인스타그램 앱이 설치되어 있고 intent를 처리할 수 있는지 확인
        val packageManager = activity.packageManager
        if (intent.resolveActivity(packageManager) != null) {
            activity.startActivityForResult(intent, 0)
        } else {
            Toast.makeText(activity, "Instagram 앱이 설치되어 있지 않습니다.", Toast.LENGTH_SHORT).show()
        }
    }
}