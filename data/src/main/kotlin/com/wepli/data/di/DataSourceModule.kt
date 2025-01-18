package com.wepli.data.di

import com.wepli.data.applemusic.datasource.AppleMusicDataSource
import com.wepli.data.applemusic.datasource.AppleMusicDataSourceImpl
import com.wepli.data.artist.datasource.remote.ArtistDataSource
import com.wepli.data.artist.datasource.remote.ArtistDataSourceImpl
import com.wepli.data.chart.datasource.remote.ChartDataSource
import com.wepli.data.chart.datasource.remote.ChartDataSourceImpl
import com.wepli.data.datastore.local.DataStorePrefDataSource
import com.wepli.data.datastore.local.DataStorePrefDataSourceImpl
import com.wepli.data.di.qualifier.RemoteDataSource
import com.wepli.data.di.qualifier.SupabaseDataSource
import com.wepli.data.keyword.datasource.KeywordDatasource
import com.wepli.data.keyword.datasource.KeywordSupabaseDatasourceImpl
import com.wepli.data.playlist.datasource.remote.PlaylistDataSource
import com.wepli.data.playlist.datasource.remote.PlaylistDataSourceImpl
import com.wepli.data.relaylist.datasource.remote.RelaylistDataSource
import com.wepli.data.relaylist.datasource.remote.RelaylistDataSourceImpl
import com.wepli.data.relaylist.datasource.remote.RelaylistSupabaseDataSourceImpl
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
    fun bindDataStoreDataSource(dataStoreDataSourceImpl: DataStorePrefDataSourceImpl): DataStorePrefDataSource

    /**
     * Supabase
     */
    @Binds
    @Singleton
    @SupabaseDataSource
    fun bindRelaylistSupabaseDataSource(relaylistSupabaseDataSourceImpl: RelaylistSupabaseDataSourceImpl): RelaylistDataSource

    @Binds
    @Singleton
    @SupabaseDataSource
    fun bindKeywordDataSource(keywordDataSourceImpl: KeywordSupabaseDatasourceImpl): KeywordDatasource
}