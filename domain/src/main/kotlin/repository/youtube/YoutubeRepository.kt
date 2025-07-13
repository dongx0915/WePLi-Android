package repository.youtube

import com.wepli.core.kotlin.flow.FlowResult
import model.musicvideo.MusicVideo

interface YoutubeRepository {

    fun searchMusicVideo(searchQuery: String): FlowResult<MusicVideo>
}