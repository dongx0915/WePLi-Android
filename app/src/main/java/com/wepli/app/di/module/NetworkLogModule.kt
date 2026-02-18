package com.wepli.app.di.module

import com.wepli.data.network.baseurl.BaseUrl
import com.wepli.devmode.network.data.model.BaseUrlInfo
import com.wepli.devmode.network.data.model.BaseUrlMatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkLogModule {

    @Provides
    @Singleton
    fun provideBaseUrlMatcher(): BaseUrlMatcher {
        return object : BaseUrlMatcher {
            override fun match(fullUrl: String): BaseUrlInfo {
                val matchedBaseUrl = BaseUrl.entries.firstOrNull { fullUrl.startsWith(it.url) } ?: BaseUrl.UNKNOWN
                return BaseUrlInfo(
                    type = matchedBaseUrl.value,
                    url = matchedBaseUrl.url
                )
            }
        }
    }
}
