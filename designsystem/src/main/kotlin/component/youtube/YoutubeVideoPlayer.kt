package component.youtube

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.LifecycleOwner
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

@Composable
fun YoutubeVideoPlayer(
    videoId: String,
    forcePause: Boolean = false,
    needAutoPlay: Boolean = false,
    autoPlayStartSeconds: Float = 0f,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    modifier: Modifier = Modifier
) {
    var youTubePlayer: YouTubePlayer? by remember { mutableStateOf(null) }

    LaunchedEffect(forcePause) {
        youTubePlayer?.let { player ->
            if (forcePause) {
                player.pause()
            }
        }
    }

    AndroidView(
        modifier = modifier.fillMaxWidth(),
        factory = { context ->
            YouTubePlayerView(context).apply {
                lifecycleOwner.lifecycle.addObserver(this)

                addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                    override fun onReady(player: YouTubePlayer) {
                        youTubePlayer = player

                        if (needAutoPlay) {
                            player.loadVideo(videoId, autoPlayStartSeconds)
                        } else {
                            player.cueVideo(videoId, 0f)
                        }
                    }
                })
            }
        }
    )
}