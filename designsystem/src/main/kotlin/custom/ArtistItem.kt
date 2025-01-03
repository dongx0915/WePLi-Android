package custom

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.wepli.designsystem.R
import com.wepli.shared.feature.mock.artistMockData
import com.wepli.uimodel.artist.ArtistUiData
import extensions.compose.toPx
import theme.WepliTheme


@Preview
@Composable
fun ArtistProfileListItemPreview() {
    ArtistProfileListItem(artist = artistMockData[0])
}

@Composable
fun ArtistProfileListItem(
    artist: ArtistUiData
) {
    val isInPreview = LocalInspectionMode.current
    Column(
        modifier = Modifier.width(92.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (isInPreview) {
            Image(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape),
                painter = painterResource(id = R.drawable.img_placeholder_artist_profile),
                contentDescription = null
            )
        } else {
            SubcomposeAsyncImage(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(artist.profileUrl)
                    .size(80.dp.toPx())
                    .build(),
                contentScale = ContentScale.Crop,
                loading = {
                    Box(modifier = Modifier.size(16.dp)) {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }
                },
                contentDescription = null
            )
        }

        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = artist.name,
            style = WepliTheme.typo.body6,
            color = WepliTheme.color.gray600,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}
