package com.wepli.data.network.interceptor

import com.wepli.data.network.baseurl.BaseUrl

enum class DebugUrlType(val url: String, val value: String) {
    POSTMAN(url = BaseUrl.POST_MAN, value = "Postman API"),
    APPLE_MUSIC(url = BaseUrl.APPLE_MUSIC, value = "Apple Music API"),
    SUPABASE(url = BaseUrl.SUPABASE, value = "Supabase API"),
    UNKNOWN(url = "", value = "Unknown API");
}