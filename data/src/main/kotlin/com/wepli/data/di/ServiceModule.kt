package com.wepli.data.di

import com.wepli.data.artist.ArtistApi
import com.wepli.data.chart.ChartApi
import com.wepli.data.di.qualifier.BaseRetrofit
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
    fun provideChartService(@BaseRetrofit retrofit: Retrofit): ChartApi = retrofit.create(ChartApi::class.java)

    @Provides
    @Singleton
    fun provideArtistsService(@BaseRetrofit retrofit: Retrofit): ArtistApi = retrofit.create(ArtistApi::class.java)

    @Provides
    @Singleton
    fun providePlaylistService(@BaseRetrofit retrofit: Retrofit): PlaylistApi = retrofit.create(PlaylistApi::class.java)

    @Provides
    @Singleton
    fun provideRelaylistService(@BaseRetrofit retrofit: Retrofit): RelaylistApi = retrofit.create(RelaylistApi::class.java)
}