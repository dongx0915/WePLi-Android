package com.wepli.data.relaylist.datasource.remote

import com.wepli.core.kotlin.FlowResult
import com.wepli.data.relaylist.response.RelaylistResponse

interface RelaylistDataSource {

    fun getRelaylists(): FlowResult<List<RelaylistResponse>>
}