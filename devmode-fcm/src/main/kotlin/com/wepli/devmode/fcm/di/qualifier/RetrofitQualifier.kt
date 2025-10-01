package com.wepli.devmode.fcm.di.qualifier

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class FcmRetrofit


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class FcmOkHttpClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class FcmDataStore