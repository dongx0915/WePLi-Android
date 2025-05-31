package com.wepli.data.playlist.datasource.remote

import com.wepli.data.playlist.response.RecommendPlaylistResponse
import com.wepli.core.kotlin.flow.FlowResult
import com.wepli.data.supabase.SupabaseConstants
import com.wepli.data.playlist.response.PlaylistResponse
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Columns
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PlaylistSupabaseDataSourceImpl @Inject constructor(
    private val supabaseClient: SupabaseClient,
) : PlaylistDataSource {

    override fun getRecommendPlaylist(): FlowResult<List<RecommendPlaylistResponse>> = flow {
        val result = runCatching {
            supabaseClient.postgrest[SupabaseConstants.PLAYLIST_TABLE]
                .select(
                    columns = Columns.list("id", "title", "cover_img"),
                    request = {
                        filter {
                            lte("id", 8)
                        }
                    }
                )
                .decodeList<RecommendPlaylistResponse>()
                .ifEmpty { throw Exception("Recommend playlist not found") }
        }

        emit(result)
    }

    override fun getThemePlaylist(): FlowResult<List<RecommendPlaylistResponse>> = flow {
        val result = runCatching {
            supabaseClient.postgrest[SupabaseConstants.PLAYLIST_TABLE]
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

    override fun findPlaylistById(playlistId: Int): FlowResult<PlaylistResponse> = flow {
        val result = runCatching {
            supabaseClient.postgrest[SupabaseConstants.PLAYLIST_VIEW]
                .select(
                    columns = Columns.ALL,
                    request = {
                        filter {
                            eq("playlist_id", playlistId)
                        }
                    }
                )
                .decodeSingle<PlaylistResponse>()
        }

        emit(result)
    }
}