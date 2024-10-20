package repository.chart

import com.wepli.core.kotlin.FlowResult
import model.music.ChartMusic


interface ChartRepository {

    suspend fun getTopChart(): FlowResult<List<ChartMusic>>
}