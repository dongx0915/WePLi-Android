package repository.playlist

import common.FlowResult
import model.playlist.RecommendPlaylist

interface PlaylistRepository {

    fun getRecommendPlaylist(): FlowResult<List<RecommendPlaylist>>

    fun getThemePlaylist(): FlowResult<List<RecommendPlaylist>>
}