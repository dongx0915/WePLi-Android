package repository.relaylist

import com.wepli.core.kotlin.FlowResult
import model.relaylist.Relaylist

interface RelaylistRepository {

    fun getRelaylists(): FlowResult<List<Relaylist>>
}