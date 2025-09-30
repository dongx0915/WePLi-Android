package com.wepli.devmode.fcm.data.model

data class FcmResponse(
    val name: String? = null,
    val error: FcmError? = null
) {

    data class FcmError(
        val code: Int,
        val message: String,
        val status: String,
    )
}