package com.wepli.data.relaylist.repository

import com.wepli.core.kotlin.FlowResult
import com.wepli.data.di.qualifier.SupabaseDataSource
import com.wepli.data.network.toEntityResult
import com.wepli.data.relaylist.datasource.remote.RelaylistDataSource
import com.wepli.data.relaylist.response.toEntities
import com.wepli.data.relaylist.response.toEntity
import model.relaylist.Relaylist
import repository.relaylist.RelaylistRepository
import javax.inject.Inject

class RelaylistRepositoryImpl @Inject constructor(
    @SupabaseDataSource private val relaylistDataSource: RelaylistDataSource,
) : RelaylistRepository {

    override fun getRelaylistById(id: Int): FlowResult<Relaylist> {
        return relaylistDataSource.getRelaylistById(id).toEntityResult {
            it.toEntity()
        }
    }

    override fun getRelaylists(): FlowResult<List<Relaylist>> {
        return relaylistDataSource.getRelaylists().toEntityResult {
            it.toEntities()
        }
    }
}