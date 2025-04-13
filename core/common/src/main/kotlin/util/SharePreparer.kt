package util

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import extensions.saveBitmapToCache

object SharePreparer {

    fun prepareShare(
        context: Context,
        bitmap: Bitmap,
        onPrepared: ((Uri, String) -> Unit),
        onFailure: (() -> Unit),
    ) {
        bitmap.saveBitmapToCache(
            context,
            "wepli_${System.currentTimeMillis()}",
            onSuccess = onPrepared,
            onFailure = onFailure,
        )
    }
}