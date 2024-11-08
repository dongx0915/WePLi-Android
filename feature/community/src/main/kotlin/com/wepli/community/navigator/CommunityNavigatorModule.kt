package com.wepli.community.navigator

import com.wepli.navigator.feature.community.CommunityDetailNavigator
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
internal abstract class CommunityNavigatorModule {

    @Binds
    @Singleton
    abstract fun bindCommunityDetailNavigator(impl: CommunityDetailNavigatorImpl): CommunityDetailNavigator
}