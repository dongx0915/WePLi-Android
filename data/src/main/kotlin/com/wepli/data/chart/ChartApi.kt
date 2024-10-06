package com.wepli.data.chart

import com.wepli.data.chart.response.ChartListResponse
import retrofit2.http.GET

interface ChartApi {

    @GET("api/chart")
    suspend fun getTopChart(): ChartListResponse
}