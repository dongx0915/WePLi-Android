package com.wepli.data.relaylist

import com.wepli.core.kotlin.FlowResult
import com.wepli.data.relaylist.response.RelaylistResponse
import retrofit2.http.GET

interface RelaylistApi {

    @GET("api/relaylists")
    fun getRelaylists(): FlowResult<List<RelaylistResponse>>
}