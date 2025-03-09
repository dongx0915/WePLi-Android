package com.wepli.data.relaylist.datasource.remote

import com.wepli.core.kotlin.flow.FlowResult
import com.wepli.data.relaylist.response.RelaylistResponse

interface RelaylistDataSource {

    fun getRelaylistById(id: Int): FlowResult<RelaylistResponse>
    fun getRelaylists(): FlowResult<List<RelaylistResponse>>
}