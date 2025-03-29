package com.wepli.data.applemusic.common.response.base

import kotlinx.serialization.Serializable

@Serializable
data class AppleDataWrapper<D>(
    val data: List<D> = emptyList(),
)