package com.wepli.data.di

import com.wepli.data.artist.datasource.remote.ArtistDataSource
import com.wepli.data.artist.datasource.remote.ArtistDataSourceImpl
import com.wepli.data.chart.datasource.remote.ChartDataSource
import com.wepli.data.chart.datasource.remote.ChartDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
interface DataSourceModule {

    @Binds
    @Singleton
    fun bindChartDataSource(chartDataSourceImpl: ChartDataSourceImpl): ChartDataSource

    @Binds
    @Singleton
    fun bindArtistDataSource(artistDataSourceImpl: ArtistDataSourceImpl): ArtistDataSource
}