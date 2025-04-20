package com.wepli.data.playlist.repository

import com.wepli.data.network.toEntityResult
import com.wepli.data.playlist.datasource.remote.PlaylistDataSource
import com.wepli.data.playlist.response.toEntities
import com.wepli.core.kotlin.flow.FlowResult
import com.wepli.data.di.qualifier.SupabaseDataSource
import com.wepli.data.playlist.response.toPlaylist
import kotlinx.coroutines.flow.flowOf
import model.playlist.Playlist
import model.playlist.RecommendPlaylist
import repository.playlist.PlaylistRepository
import java.util.concurrent.atomic.AtomicReference
import javax.inject.Inject

class PlaylistRepositoryImpl @Inject constructor(
    @SupabaseDataSource private val playlistDatasource: PlaylistDataSource,
) : PlaylistRepository {

    private var cachedPlaylist: AtomicReference<Playlist> = AtomicReference(null)

    override fun getRecommendPlaylist(): FlowResult<List<RecommendPlaylist>> {
        return playlistDatasource.getRecommendPlaylist().toEntityResult {
            it.toEntities()
        }
    }

    override fun getThemePlaylist(): FlowResult<List<RecommendPlaylist>> {
        return playlistDatasource.getThemePlaylist().toEntityResult {
            it.toEntities()
        }
    }

    override fun getPlaylistById(playlistId: Int): FlowResult<Playlist> {
        cachedPlaylist.getAndSet(null)?.let { playlist ->
            return flowOf(Result.success(playlist))
        }

        return playlistDatasource.findPlaylistById(playlistId).toEntityResult {
            it.toPlaylist().also { newPlaylist ->
                cachedPlaylist.set(newPlaylist)
            }
        }
    }
}