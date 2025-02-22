package image

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter.State
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageScope
import coil.request.ImageRequest
import extensions.compose.toPx

@Composable
fun AsyncImageWithPreview(
    modifier: Modifier = Modifier,
    imageUrl: String,
    previewImage: Painter? = null,
    imageOverrideSize: Dp? = null,
    contentScale: ContentScale = ContentScale.Crop,
    allowHardware: Boolean = true,
    onError: ((State.Error) -> Unit)? = null,
    onSuccess: ((State.Success) -> Unit)? = null,
    loadingContent: @Composable (() -> Unit)? = null,
    errorContent: @Composable (SubcomposeAsyncImageScope.(State.Error) -> Unit)? = null,
    successContent: @Composable (SubcomposeAsyncImageScope.(State.Success) -> Unit)? = null,
) {
    val isInPreview = LocalInspectionMode.current
    val context = LocalContext.current
    val imageSizePx = imageOverrideSize?.toPx()

    val imageRequest = remember(imageUrl) {
        ImageRequest.Builder(context).apply {
            data(imageUrl)
            crossfade(true)
            imageSizePx?.let(::size)
            allowHardware(allowHardware)
        }.build()
    }

    if (isInPreview && previewImage != null) {
        Image(
            modifier = modifier,
            painter = previewImage,
            contentDescription = null,
        )
    } else {
        SubcomposeAsyncImage(
            model = imageRequest,
            contentDescription = null,
            modifier = modifier,
            contentScale = contentScale,
            loading = {
                loadingContent?.invoke() ?: CircularProgressIndicator(
                    modifier = Modifier
                        .size(16.dp)
                        .align(Alignment.Center),
                    strokeWidth = 2.dp
                )
            },
            success = successContent,
            error = errorContent,
            onSuccess = onSuccess,
            onError = onError,
        )
    }
}