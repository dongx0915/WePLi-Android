package repository.relaylist

import com.wepli.core.kotlin.flow.FlowResult
import model.relaylist.Relaylist

interface RelaylistRepository {

    fun getRelaylistById(id: Int): FlowResult<Relaylist>
    fun getRelaylists(): FlowResult<List<Relaylist>>
}