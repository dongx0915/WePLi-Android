package com.wepli.data.relaylist.repository

import com.wepli.core.kotlin.flow.FlowResult
import com.wepli.data.di.qualifier.SupabaseDataSource
import com.wepli.data.network.toEntityResult
import com.wepli.data.relaylist.datasource.remote.RelaylistDataSource
import com.wepli.data.relaylist.response.toEntities
import com.wepli.data.relaylist.response.toEntity
import kotlinx.coroutines.flow.flowOf
import model.relaylist.Relaylist
import repository.relaylist.RelaylistRepository
import java.util.concurrent.atomic.AtomicReference
import javax.inject.Inject

class RelaylistRepositoryImpl @Inject constructor(
    @SupabaseDataSource private val relaylistDataSource: RelaylistDataSource,
) : RelaylistRepository {

    private var cachedRelaylist: AtomicReference<Relaylist> = AtomicReference(null)

    override fun getRelaylistById(id: Int): FlowResult<Relaylist> {
        cachedRelaylist.getAndSet(null)?.let { relaylist ->
            return flowOf(Result.success(relaylist))
        }

        return relaylistDataSource.getRelaylistById(id).toEntityResult { dto ->
            dto.toEntity().also { newRelaylist ->
                cachedRelaylist.set(newRelaylist)
            }
        }
    }

    override fun getRelaylists(): FlowResult<List<Relaylist>> {
        return relaylistDataSource.getRelaylists().toEntityResult {
            it.toEntities()
        }
    }
}