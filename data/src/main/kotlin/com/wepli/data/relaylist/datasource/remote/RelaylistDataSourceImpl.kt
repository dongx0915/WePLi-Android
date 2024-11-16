package com.wepli.data.relaylist.datasource.remote

import com.wepli.core.kotlin.FlowResult
import com.wepli.data.relaylist.RelaylistApi
import com.wepli.data.relaylist.response.RelaylistResponse
import javax.inject.Inject

class RelaylistDataSourceImpl @Inject constructor(
    private val relaylistApi: RelaylistApi,
): RelaylistDataSource {

    override fun getRelaylists(): FlowResult<List<RelaylistResponse>> {
        return relaylistApi.getRelaylists()
    }
}