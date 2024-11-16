package com.wepli.data.di

import com.wepli.data.artist.ArtistApi
import com.wepli.data.chart.ChartApi
import com.wepli.data.playlist.PlaylistApi
import com.wepli.data.relaylist.RelaylistApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Provides
    @Singleton
    fun provideChartService(retrofit: Retrofit): ChartApi = retrofit.create(ChartApi::class.java)

    @Provides
    @Singleton
    fun provideArtistsService(retrofit: Retrofit): ArtistApi = retrofit.create(ArtistApi::class.java)

    @Provides
    @Singleton
    fun providePlaylistService(retrofit: Retrofit): PlaylistApi = retrofit.create(PlaylistApi::class.java)

    @Provides
    @Singleton
    fun provideRelaylistService(retrofit: Retrofit): RelaylistApi = retrofit.create(RelaylistApi::class.java)
}