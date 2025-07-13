package repository.chart

import com.wepli.core.kotlin.flow.FlowResult
import model.music.ChartMusic


interface ChartRepository {

    fun getTopChart(): FlowResult<List<ChartMusic>>
}