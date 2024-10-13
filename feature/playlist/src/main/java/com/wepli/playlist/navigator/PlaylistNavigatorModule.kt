package com.wepli.playlist.navigator

import com.wepli.navigator.feature.playlist.PlaylistNavigator
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
internal abstract class PlaylistNavigatorModule {

    @Binds
    @Singleton
    abstract fun bindPlaylistNavigator(impl: PlaylistNavigatorImpl): PlaylistNavigator
}