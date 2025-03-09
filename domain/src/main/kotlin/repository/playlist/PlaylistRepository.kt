package repository.playlist

import com.wepli.core.kotlin.flow.FlowResult
import model.playlist.Playlist
import model.playlist.RecommendPlaylist

interface PlaylistRepository {

    fun getRecommendPlaylist(): FlowResult<List<RecommendPlaylist>>

    fun getThemePlaylist(): FlowResult<List<RecommendPlaylist>>

    fun getPlaylistById(playlistId: Int): FlowResult<Playlist>
}