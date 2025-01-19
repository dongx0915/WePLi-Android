package com.wepli.data.playlist.datasource.remote

import com.wepli.data.playlist.PlaylistApi
import com.wepli.data.playlist.response.RecommendPlaylistResponse
import com.wepli.core.kotlin.FlowResult
import com.wepli.data.playlist.response.PlaylistResponse
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Columns
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PlaylistDataSourceImpl @Inject constructor(
    private val supabaseClient: SupabaseClient,
    private val playlistApi: PlaylistApi,
) : PlaylistDataSource {

    companion object {
        private const val PLAYLIST_VIEW = "playlist_view"
    }

    override fun getRecommendPlaylist(): FlowResult<List<RecommendPlaylistResponse>> {
        return playlistApi.getRecommendPlaylist()
    }

    override fun getThemePlaylist(): FlowResult<List<RecommendPlaylistResponse>> {
        return playlistApi.getThemePlaylist()
    }

    override fun findPlaylistById(playlistId: Int): FlowResult<List<PlaylistResponse>> = flow {
        val result = runCatching {
            supabaseClient.postgrest[PLAYLIST_VIEW]
                .select(
                    columns = Columns.ALL,
                    request = {
                        filter {
                            eq("playlist_id", playlistId)
                        }
                    }
                )
                .decodeList<PlaylistResponse>()
                .ifEmpty { throw Exception("Playlist not found") }
        }

        emit(result)
    }
}