package com.wepli.data.playlist.repository

import com.wepli.data.network.toEntityResult
import com.wepli.data.playlist.datasource.remote.PlaylistDataSource
import com.wepli.data.playlist.response.toEntities
import common.FlowResult
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
}