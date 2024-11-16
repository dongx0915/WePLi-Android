package com.wepli.data.di

import com.wepli.data.artist.repository.ArtistRepositoryImpl
import repository.chart.ChartRepository
import com.wepli.data.chart.repository.ChartRepositoryImpl
import com.wepli.data.playlist.repository.PlaylistRepositoryImpl
import com.wepli.data.relaylist.repository.RelaylistRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import repository.artist.ArtistRepository
import repository.playlist.PlaylistRepository
import repository.relaylist.RelaylistRepository
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

    @Binds
    @Singleton
    fun bindPlaylistRepository(playlistRepositoryImpl: PlaylistRepositoryImpl): PlaylistRepository

    @Binds
    @Singleton
    fun bindRelaylistRepository(relaylistRepositoryImpl: RelaylistRepositoryImpl): RelaylistRepository
}