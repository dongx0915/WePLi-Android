package com.wepli.data.di.qualifier

import javax.inject.Qualifier


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BaseOkHttpClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AppleMusicOkHttpClient