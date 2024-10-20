package com.wepli.data.chart.repository

import com.wepli.data.chart.datasource.remote.ChartDataSource
import com.wepli.data.chart.response.toEntities
import com.wepli.data.network.toEntityResult
import com.wepli.core.kotlin.FlowResult
import model.music.ChartMusic
import repository.chart.ChartRepository
import javax.inject.Inject

class ChartRepositoryImpl @Inject constructor(
    private val chartDataSource: ChartDataSource
) : ChartRepository {

    override suspend fun getTopChart(): FlowResult<List<ChartMusic>> {
        return chartDataSource.getTopChart().toEntityResult {
            it.toEntities()
        }
    }
}