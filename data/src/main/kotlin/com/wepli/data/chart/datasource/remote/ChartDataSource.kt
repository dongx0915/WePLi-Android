package com.wepli.data.chart.datasource.remote

import com.wepli.data.chart.response.ChartListResponse
import common.FlowResult

interface ChartDataSource {

    suspend fun getTopChart(): FlowResult<ChartListResponse>
}