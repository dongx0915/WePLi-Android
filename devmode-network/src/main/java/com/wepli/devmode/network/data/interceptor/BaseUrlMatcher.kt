package com.wepli.devmode.network.data.interceptor

data class BaseUrlInfo(
    val type: String,
    val url: String
)

interface BaseUrlMatcher {
    fun match(fullUrl: String): BaseUrlInfo
}
