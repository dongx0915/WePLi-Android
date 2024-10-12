package repository.chart

import com.wepli.kotlin.FlowResult
import model.music.ChartMusic


interface ChartRepository {

    suspend fun getTopChart(): FlowResult<List<ChartMusic>>
}