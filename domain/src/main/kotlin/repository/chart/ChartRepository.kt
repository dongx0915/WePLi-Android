package repository.chart

import common.FlowResult
import model.music.ChartMusic


interface ChartRepository {

    suspend fun getTopChart(): FlowResult<List<ChartMusic>>
}