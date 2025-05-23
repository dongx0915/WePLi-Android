package util.share

import android.net.Uri

sealed interface ShareType {
    data class Instagram(val backgroundUri: Uri? = null) : ShareType
}