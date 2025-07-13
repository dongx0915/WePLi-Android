package com.wepli.data.di.repository

import com.wepli.data.applemusic.repository.AppleMusicRepositoryImpl
import com.wepli.data.artist.repository.ArtistRepositoryImpl
import repository.chart.ChartRepository
import com.wepli.data.chart.repository.ChartRepositoryImpl
import com.wepli.data.keyword.repository.KeywordRepositoryImpl
import com.wepli.data.playlist.repository.PlaylistRepositoryImpl
import com.wepli.data.post.repository.PostRepositoryImpl
import com.wepli.data.relaylist.repository.RelaylistRepositoryImpl
import com.wepli.data.song.repository.SongRepository
import com.wepli.data.song.repository.SongRepositoryImpl
import com.wepli.data.supabase.bucket.repository.SupabaseBucketRepositoryImpl
import com.wepli.data.user.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import debug.repository.DebugApiLogRepository
import com.wepli.data.network.apilog.DebugApiLogRepositoryImpl
import com.wepli.data.youtube.repository.YoutubeRepository
import com.wepli.data.youtube.repository.YoutubeRepositoryImpl
import model.recommend.repository.KeywordRepository
import model.supabase.repository.SupabaseBucketRepository
import repository.applemusic.AppleMusicRepository
import repository.artist.ArtistRepository
import repository.playlist.PlaylistRepository
import repository.post.PostRepository
import repository.relaylist.RelaylistRepository
import repository.user.UserRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun bindUserRepository(userRepository: UserRepositoryImpl): UserRepository

    @Binds
    @Singleton
    fun bindPostRepository(postRepositoryImpl: PostRepositoryImpl): PostRepository

    @Binds
    @Singleton
    fun bindSongRepository(songRepositoryImpl: SongRepositoryImpl): SongRepository

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

    @Binds
    @Singleton
    fun bindKeywordRepository(keywordRepositoryImpl: KeywordRepositoryImpl): KeywordRepository

    @Binds
    @Singleton
    fun bindAppleMusicRepository(appleMusicRepositoryImpl: AppleMusicRepositoryImpl): AppleMusicRepository

    @Binds
    @Singleton
    fun bindYoutubeRepository(youtubeRepositoryImpl: YoutubeRepositoryImpl): YoutubeRepository

    @Binds
    @Singleton
    fun bindDebugApiLogRepository(debugApiLogRepositoryImpl: DebugApiLogRepositoryImpl): DebugApiLogRepository
}