package com.wepli.devmode.network.data.model

data class BaseUrlInfo(
    val type: String,
    val url: String
)

interface BaseUrlMatcher {
    fun match(fullUrl: String): BaseUrlInfo
}
