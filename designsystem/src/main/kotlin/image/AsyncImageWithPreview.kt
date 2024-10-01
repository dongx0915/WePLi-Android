package image

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageScope
import coil.request.ImageRequest
import extensions.toPx

@Composable
fun AsyncImageWithPreview(
    modifier: Modifier = Modifier,
    imageUrl: String,
    previewImage: Painter,
    size: Dp,
    shape: Shape,
    contentScale: ContentScale = ContentScale.Crop,
    loadingContent: @Composable (() -> Unit)? = null,
    errorContent: @Composable (SubcomposeAsyncImageScope.(AsyncImagePainter.State.Error) -> Unit)? = null,
    successContent: @Composable (SubcomposeAsyncImageScope.(AsyncImagePainter.State.Success) -> Unit)? = null,
) {
    val isInPreview = LocalInspectionMode.current
    val context = LocalContext.current
    val imageSizePx = size.toPx()

    val imageRequest = remember(imageUrl) {
        ImageRequest.Builder(context)
            .data(imageUrl)
            .size(imageSizePx)
            .build()
    }

    if (isInPreview) {
        Image(
            modifier = modifier
                .size(size)
                .clip(shape),
            painter = previewImage,
            contentDescription = null,
        )
    } else {
        // SubcomposeAsyncImage 사용: 모든 파라미터를 노출해 원하는 대로 사용 가능
        SubcomposeAsyncImage(
            model = imageRequest,
            contentDescription = null,
            modifier = modifier
                .size(size)
                .clip(shape),
            contentScale = contentScale,
            loading = {
                // 로딩 중 표시할 UI (사용자가 원하는 콘텐츠를 설정할 수 있음)
                loadingContent?.invoke() ?: CircularProgressIndicator(modifier = Modifier.size(16.dp))
            },
            error = errorContent,
            success = successContent
        )
    }
}