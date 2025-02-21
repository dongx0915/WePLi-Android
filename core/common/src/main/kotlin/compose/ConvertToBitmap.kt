package compose

import android.graphics.Bitmap
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.drawToBitmap

@Composable
fun convertToBitmap(
    targetContent: @Composable () -> Unit
) : () -> Bitmap? {
    val context = LocalContext.current
    val composeView = remember { ComposeView(context) }

    fun captureBitmap(): Bitmap? {
        return if (composeView.isLaidOut) {
            composeView.drawToBitmap()
        } else {
            null
        }
    }

    AndroidView(
        factory = {
            composeView.apply {
                setContent { targetContent() }
            }
        },
    )

    return ::captureBitmap
}