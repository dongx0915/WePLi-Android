package com.wepli.data.applemusic.common.response.base

import kotlinx.serialization.Serializable

@Serializable
open class AppleCatalogBaseResponse<T>(
    val chart: String? = null,
    val name: String? = null,
    val orderId: String? = null,
    val href: String? = null,
    val next: String? = null,
    val data: List<T>? = null,
)