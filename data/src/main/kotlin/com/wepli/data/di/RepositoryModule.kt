package com.wepli.data.di

import com.wepli.data.artist.repository.ArtistRepositoryImpl
import repository.chart.ChartRepository
import com.wepli.data.chart.repository.ChartRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import repository.artist.ArtistRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun bindChartRepository(chartRepositoryImpl: ChartRepositoryImpl): ChartRepository

    @Binds
    @Singleton
    fun bindArtistRepository(artistRepositoryImpl: ArtistRepositoryImpl): ArtistRepository
}