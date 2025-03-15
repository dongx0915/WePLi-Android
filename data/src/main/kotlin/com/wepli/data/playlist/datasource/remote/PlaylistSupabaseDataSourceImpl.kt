package com.wepli.data.playlist.datasource.remote

import com.wepli.data.playlist.response.RecommendPlaylistResponse
import com.wepli.core.kotlin.flow.FlowResult
import com.wepli.data.playlist.response.PlaylistResponse
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Columns
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PlaylistSupabaseDataSourceImpl @Inject constructor(
    private val supabaseClient: SupabaseClient,
) : PlaylistDataSource {

    companion object {
        private const val PLAYLIST_TABLE = "playlist"
        private const val PLAYLIST_VIEW = "playlist_view"
    }

    override fun getRecommendPlaylist(): FlowResult<List<RecommendPlaylistResponse>> = flow {
        val result = runCatching {
            supabaseClient.postgrest[PLAYLIST_TABLE]
                .select(
                    columns = Columns.list("id", "title", "cover_img"),
                    request = {
                        filter {
                            lte("id", 8)
                        }
                    }
                )
                .decodeList<RecommendPlaylistResponse>()
                .ifEmpty { throw Exception("Theme playlist not found") }
        }

        emit(result)
    }

    override fun getThemePlaylist(): FlowResult<List<RecommendPlaylistResponse>> = flow {
        val result = runCatching {
            supabaseClient.postgrest[PLAYLIST_TABLE]
                .select(
                    columns = Columns.list("id", "title", "cover_img"),
                    request = {
                        filter {
                            gte("id", 9)
                        }
                    }
                )
                .decodeList<RecommendPlaylistResponse>()
                .ifEmpty { throw Exception("Theme playlist not found") }
        }

        emit(result)
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