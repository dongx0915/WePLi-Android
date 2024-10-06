package com.wepli.data.chart.datasource.remote

import com.wepli.data.chart.ChartApi
import com.wepli.data.chart.response.ChartListResponse
import javax.inject.Inject

class ChartDataSourceImpl @Inject constructor(
    private val chartApi: ChartApi
) : ChartDataSource {

    override suspend fun getTopChart(): ChartListResponse {
        return chartApi.getTopChart()
    }
}