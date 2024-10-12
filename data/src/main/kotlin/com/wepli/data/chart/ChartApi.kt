package com.wepli.data.chart

import com.wepli.data.chart.response.ChartListResponse
import com.wepli.kotlin.FlowResult
import retrofit2.http.GET

interface ChartApi {

    @GET("api/chart")
    fun getTopChart(): FlowResult<ChartListResponse>
}