package com.wepli.data.chart.datasource.remote

import com.wepli.data.chart.response.ChartListResponse

interface ChartDataSource {

    suspend fun getTopChart(): ChartListResponse
}