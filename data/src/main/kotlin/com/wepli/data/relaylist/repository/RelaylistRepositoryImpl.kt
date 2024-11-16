package com.wepli.data.relaylist.repository

import com.wepli.core.kotlin.FlowResult
import com.wepli.data.network.toEntityResult
import com.wepli.data.relaylist.datasource.remote.RelaylistDataSource
import com.wepli.data.relaylist.response.toEntities
import model.relaylist.Relaylist
import repository.relaylist.RelaylistRepository
import javax.inject.Inject

class RelaylistRepositoryImpl @Inject constructor(
    private val relaylistDataSource: RelaylistDataSource,
) : RelaylistRepository {

    override fun getRelaylists(): FlowResult<List<Relaylist>> {
        return relaylistDataSource.getRelaylists().toEntityResult {
            it.toEntities()
        }
    }
}