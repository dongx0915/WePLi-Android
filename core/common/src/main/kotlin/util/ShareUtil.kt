package util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.core.content.FileProvider
import java.io.File
import com.wepli.core.resources.R as CoreR

object ShareUtil {
    private const val IMAGE_TYPE: String = "image/*"
    private const val INSTAGRAM_PACKAGE_NAME: String = "com.instagram.android"

    // 스토리로 바로 공유
    fun shareToInstagramStory(
        activity: Activity,
        stickerAssetUri: Uri,
        backgroundAssetUri: Uri? = null,
    ) {
        if (isAppInstalled(activity, INSTAGRAM_PACKAGE_NAME).not()) {
            Toast.makeText(activity, "Instagram을 설치해주세요.", Toast.LENGTH_SHORT).show()
            return
        }

        val facebookAppId = activity.getString(CoreR.string.facebook_app_id)
        val intent = Intent("com.instagram.share.ADD_TO_STORY").apply {
            setType("image/*")
            putExtra("source_application", facebookAppId)
            putExtra("interactive_asset_uri", stickerAssetUri)
            backgroundAssetUri?.let { setDataAndType(it, IMAGE_TYPE) }
            flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        }

        // 스티커에 대한 권한 부여
        backgroundAssetUri?.let {
            activity.grantUriPermission(INSTAGRAM_PACKAGE_NAME, it, Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        activity.grantUriPermission(INSTAGRAM_PACKAGE_NAME, stickerAssetUri, Intent.FLAG_GRANT_READ_URI_PERMISSION)

        val packageManager = activity.packageManager
        val resolveInfo = intent.resolveActivity(packageManager)
        if (resolveInfo != null) {
            activity.startActivity(intent)
        } else {
            Intent(Intent.ACTION_SEND).apply {
                type = "image/*"
                putExtra(Intent.EXTRA_STREAM, stickerAssetUri)
                setPackage(INSTAGRAM_PACKAGE_NAME)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
        }
    }

    // 인스타 공유 (피드, 릴스, 스토리, DM 선택 가능)
    fun toInstagramIntentImage(activity: Activity, imagePath: String) {
        if (isAppInstalled(activity, INSTAGRAM_PACKAGE_NAME).not()) {
            Toast.makeText(activity, "Instagram을 설치해주세요.", Toast.LENGTH_SHORT).show()
            return
        }

        activity.run {
            val file = File(imagePath)
            val uri = FileProvider.getUriForFile(activity.applicationContext, "com.wepli.app.fileprovider", file)

            Intent(Intent.ACTION_SEND).apply {
                type = "image/jpg"
                putExtra(Intent.EXTRA_STREAM, uri)
                setPackage(INSTAGRAM_PACKAGE_NAME)
            }.let(this::startActivity)
        }
    }

    private fun isAppInstalled(context: Context, packageName: String): Boolean {
        return try {
            context.packageManager.getPackageInfo(packageName, 0)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }
}