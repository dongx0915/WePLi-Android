package util

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import extensions.saveBitmapToCache

object SharePreparer {

    fun prepareShare(
        context: Context,
        bitmap: Bitmap,
        shareType: ShareType,
        onFailure: (() -> Unit),
    ) {
        bitmap.saveBitmapToCache(
            context,
            "wepli_${System.currentTimeMillis()}",
            onSuccess = { uri, path ->
                shareBySnsType(context, shareType, uri)
            },
            onFailure = onFailure,
        )
    }

    private fun shareBySnsType(
        context: Context,
        shareType: ShareType,
        shareImage: Uri,
    ) {
        when (shareType) {
            is ShareType.Instagram -> {
                ShareUtil(context).shareToInstagramStory(shareImage, shareType.backgroundUri)
            }
        }
    }
}