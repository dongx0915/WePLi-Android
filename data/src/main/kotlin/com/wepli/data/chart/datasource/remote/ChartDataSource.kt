package com.wepli.data.chart.datasource.remote

import com.wepli.data.chart.response.ChartListResponse
import com.wepli.core.kotlin.flow.FlowResult

interface ChartDataSource {

    fun getTopChart(): FlowResult<ChartListResponse>
}