package com.wepli.data.di.datasource

import com.wepli.data.applemusic.datasource.AppleMusicDataSource
import com.wepli.data.applemusic.datasource.AppleMusicDataSourceImpl
import com.wepli.data.artist.datasource.remote.ArtistDataSource
import com.wepli.data.artist.datasource.remote.ArtistDataSourceImpl
import com.wepli.data.chart.datasource.remote.ChartDataSource
import com.wepli.data.chart.datasource.remote.ChartDataSourceImpl
import com.wepli.data.datastore.local.DataStorePrefDataSource
import com.wepli.data.datastore.local.DataStorePrefDataSourceImpl
import com.wepli.data.di.qualifier.RemoteDataSource
import com.wepli.data.devmode.apilog.datasource.DebugApiLogLocalDatasource
import com.wepli.data.devmode.apilog.datasource.DebugApiLogLocalDatasourceImpl
import com.wepli.data.devmode.fcm.datasource.DevModeFcmDataSource
import com.wepli.data.devmode.fcm.datasource.DevModeFcmDataSourceImpl
import com.wepli.data.playlist.datasource.remote.PlaylistDataSource
import com.wepli.data.playlist.datasource.remote.PlaylistDataSourceImpl
import com.wepli.data.relaylist.datasource.remote.RelaylistDataSource
import com.wepli.data.relaylist.datasource.remote.RelaylistDataSourceImpl
import com.wepli.data.youtube.datasource.remote.YoutubeRemoteDataSource
import com.wepli.data.youtube.datasource.remote.YoutubeRemoteDataSourceImpl
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
    @RemoteDataSource
    fun bindChartDataSource(chartDataSourceImpl: ChartDataSourceImpl): ChartDataSource

    @Binds
    @Singleton
    fun bindArtistDataSource(artistDataSourceImpl: ArtistDataSourceImpl): ArtistDataSource

    @Binds
    @Singleton
    fun bindPlaylistDataSource(playlistDataSourceImpl: PlaylistDataSourceImpl): PlaylistDataSource

    @Binds
    @Singleton
    @RemoteDataSource
    fun bindRelaylistDataSource(relaylistDataSourceImpl: RelaylistDataSourceImpl): RelaylistDataSource

    @Binds
    @Singleton
    fun bindAppleMusicDataSource(appleMusicDataSourceImpl: AppleMusicDataSourceImpl): AppleMusicDataSource

    @Binds
    @Singleton
    fun bindYoutubeDataSource(youtubeDataSourceImpl: YoutubeRemoteDataSourceImpl): YoutubeRemoteDataSource

    @Binds
    @Singleton
    fun bindDataStoreDataSource(dataStoreDataSourceImpl: DataStorePrefDataSourceImpl): DataStorePrefDataSource

    // DevMode
    @Binds
    @Singleton
    fun bindApiLogDataSource(dataSource: DebugApiLogLocalDatasourceImpl): DebugApiLogLocalDatasource

    @Binds
    @Singleton
    fun bindDevFcmDataSource(dataSource: DevModeFcmDataSourceImpl): DebugApiLogLocalDatasource
}