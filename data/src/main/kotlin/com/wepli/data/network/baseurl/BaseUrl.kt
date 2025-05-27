package com.wepli.data.network.baseurl

import com.wepli.core.common.BuildConfig

enum class BaseUrl(
    private val prodUrl: String,
    private val testUrl: String,
    val value: String
) {
    POSTMAN(
        prodUrl = BuildConfig.POSTMAN_URL,
        testUrl = BuildConfig.POSTMAN_URL,
        value = "Postman API"
    ),
    APPLE_MUSIC(
        prodUrl = BuildConfig.APPLE_MUSIC_URL,
        testUrl = BuildConfig.APPLE_MUSIC_URL,
        value = "Apple Music API"
    ),
    SUPABASE(
        prodUrl = BuildConfig.SUPABASE_URL,
        testUrl = BuildConfig.SUPABASE_URL,
        value = "Supabase API"
    ),
    UNKNOWN(
        prodUrl = "",
        testUrl = "",
        value = "Unknown API"
    );

    val url: String
        get() = if (BuildConfig.DEBUG) testUrl else prodUrl
}