package com.wepli.data.applemusic.common.response.base

import kotlinx.serialization.Serializable

@Serializable
open class AppleSearchBaseResult<T>(
    val href: String? = null,
    val next: String? = null,
    val data: List<T>? = null,
)