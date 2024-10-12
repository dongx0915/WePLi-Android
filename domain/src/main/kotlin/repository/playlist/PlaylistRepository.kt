package repository.playlist

import com.wepli.kotlin.FlowResult
import model.playlist.RecommendPlaylist

interface PlaylistRepository {

    fun getRecommendPlaylist(): FlowResult<List<RecommendPlaylist>>

    fun getThemePlaylist(): FlowResult<List<RecommendPlaylist>>
}