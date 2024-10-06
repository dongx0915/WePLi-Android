package repository.chart

import model.music.ChartMusic


interface ChartRepository {

    suspend fun getTopChart(): List<ChartMusic>
}