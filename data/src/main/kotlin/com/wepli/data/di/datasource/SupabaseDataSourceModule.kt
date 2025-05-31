package com.wepli.data.di.datasource

import com.wepli.data.chart.datasource.remote.ChartDataSource
import com.wepli.data.chart.datasource.remote.ChartSupabaseDataSourceImpl
import com.wepli.data.di.qualifier.SupabaseDataSource
import com.wepli.data.keyword.datasource.KeywordDatasource
import com.wepli.data.keyword.datasource.KeywordSupabaseDatasourceImpl
import com.wepli.data.playlist.datasource.remote.PlaylistDataSource
import com.wepli.data.playlist.datasource.remote.PlaylistSupabaseDataSourceImpl
import com.wepli.data.post.datasource.PostDataSource
import com.wepli.data.post.datasource.PostSupabaseDataSourceImpl
import com.wepli.data.relaylist.datasource.remote.RelaylistDataSource
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
interface SupabaseDataSourceModule {

    @Binds
    @Singleton
    @SupabaseDataSource
    fun bindUserSupabaseDataSource(userSupabaseDataSource: UserSupabaseDataSourceImpl): UserSupabaseDataSource

    @Binds
    @Singleton
    @SupabaseDataSource
    fun bindPostSupabaseDataSource(postSupabaseDataSourceImpl: PostSupabaseDataSourceImpl): PostDataSource

    @Binds
    @Singleton
    @SupabaseDataSource
    fun bindSongSupabaseDataSource(songSupabaseDataSourceImpl: SongSupabaseDataSourceImpl): SongDataSource

    @Binds
    @Singleton
    @SupabaseDataSource
    fun bindRelaylistSupabaseDataSource(relaylistSupabaseDataSourceImpl: RelaylistSupabaseDataSourceImpl): RelaylistDataSource

    @Binds
    @Singleton
    @SupabaseDataSource
    fun bindPlaylistSupabaseDataSource(playlistDataSourceImpl: PlaylistSupabaseDataSourceImpl): PlaylistDataSource

    @Binds
    @Singleton
    @SupabaseDataSource
    fun bindKeywordSupabaseDataSource(keywordDataSourceImpl: KeywordSupabaseDatasourceImpl): KeywordDatasource

    @Binds
    @Singleton
    @SupabaseDataSource
    fun bindChartSupabaseDataSource(chartDataSourceImpl: ChartSupabaseDataSourceImpl): ChartDataSource

    @Binds
    @Singleton
    @SupabaseDataSource
    fun bindSupabaseBucketDataSource(supabaseBucketDataSourceImpl: SupabaseBucketDataSourceImpl): SupabaseBucketDataSource
}