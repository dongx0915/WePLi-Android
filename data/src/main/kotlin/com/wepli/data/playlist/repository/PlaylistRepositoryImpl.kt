package com.wepli.data.playlist.repository

import com.wepli.data.network.toEntityResult
import com.wepli.data.playlist.datasource.remote.PlaylistDataSource
import com.wepli.data.playlist.response.toEntities
import com.wepli.core.kotlin.FlowResult
import com.wepli.data.playlist.response.toPlaylist
import kotlinx.coroutines.flow.emptyFlow
import model.playlist.Playlist
import model.playlist.RecommendPlaylist
import repository.playlist.PlaylistRepository
import javax.inject.Inject

class PlaylistRepositoryImpl @Inject constructor(
    private val playlistDatasource: PlaylistDataSource,
) : PlaylistRepository {

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
        return playlistDatasource.findPlaylistById(playlistId).toEntityResult {
            it.toPlaylist()
        }
    }
}