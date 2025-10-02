package com.wepli.devmode.fcm.data.model

import kotlinx.serialization.Serializable

@Serializable
data class FcmResponse(
    val name: String? = null,
    val error: FcmError? = null
) {
    @Serializable
    data class FcmError(
        val code: Int,
        val message: String,
        val status: String,
    )
}