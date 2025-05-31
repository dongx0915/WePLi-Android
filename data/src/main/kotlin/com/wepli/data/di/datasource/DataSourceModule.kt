package com.wepli.data.di.datasource

import com.wepli.data.applemusic.datasource.AppleMusicDataSource
import com.wepli.data.applemusic.datasource.AppleMusicDataSourceImpl
import com.wepli.data.artist.datasource.remote.ArtistDataSource
import com.wepli.data.artist.datasource.remote.ArtistDataSourceImpl
import com.wepli.data.chart.datasource.remote.ChartDataSource
import com.wepli.data.chart.datasource.remote.ChartDataSourceImpl
import com.wepli.data.chart.datasource.remote.ChartSupabaseDataSourceImpl
import com.wepli.data.datastore.local.DataStorePrefDataSource
import com.wepli.data.datastore.local.DataStorePrefDataSourceImpl
import com.wepli.data.di.qualifier.RemoteDataSource
import com.wepli.data.di.qualifier.SupabaseDataSource
import com.wepli.data.keyword.datasource.KeywordDatasource
import com.wepli.data.keyword.datasource.KeywordSupabaseDatasourceImpl
import com.wepli.data.playlist.datasource.remote.PlaylistDataSource
import com.wepli.data.playlist.datasource.remote.PlaylistDataSourceImpl
import com.wepli.data.playlist.datasource.remote.PlaylistSupabaseDataSourceImpl
import com.wepli.data.post.datasource.PostDataSource
import com.wepli.data.post.datasource.PostSupabaseDataSourceImpl
import com.wepli.data.relaylist.datasource.remote.RelaylistDataSource
import com.wepli.data.relaylist.datasource.remote.RelaylistDataSourceImpl
import com.wepli.data.relaylist.datasource.remote.RelaylistSupabaseDataSourceImpl
import com.wepli.data.song.datasource.SongDataSource
import com.wepli.data.song.datasource.SongSupabaseDataSourceImpl
import com.wepli.data.supabase.bucket.datasource.SupabaseBucketDataSource
import com.wepli.data.supabase.bucket.datasource.SupabaseBucketDataSourceImpl
import com.wepli.data.user.datasource.UserSupabaseDataSource
import com.wepli.data.user.datasource.UserSupabaseDataSourceImpl
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
    fun bindDataStoreDataSource(dataStoreDataSourceImpl: DataStorePrefDataSourceImpl): DataStorePrefDataSource
}