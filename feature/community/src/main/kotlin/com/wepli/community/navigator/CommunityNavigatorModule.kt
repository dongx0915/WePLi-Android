package com.wepli.community.navigator

import com.wepli.navigator.feature.community.CommunityDetailNavigator
import com.wepli.navigator.feature.community.CommunityNavigator
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
    abstract fun bindCommunityNavigator(impl: CommunityNavigatorImpl): CommunityNavigator

    @Binds
    @Singleton
    abstract fun bindCommunityDetailNavigator(impl: CommunityDetailNavigatorImpl): CommunityDetailNavigator
}