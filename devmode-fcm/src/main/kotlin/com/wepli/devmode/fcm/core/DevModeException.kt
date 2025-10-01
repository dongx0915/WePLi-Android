package com.wepli.devmode.fcm.core

class DevModeException(
    val code: Int = -1,
    override val message: String? = ERROR_MSG_UNKNOWN,
    override val cause: Throwable? = null
) : Throwable(message) {

    fun toLog() = "$message (${code})"

    companion object {
        const val ERROR_MSG_UNKNOWN = "알 수 없는 에러가 발생하였습니다."
    }
}

fun Throwable.toDevModeException(errorCode: Int = -1): DevModeException {
    return this as? DevModeException
        ?: DevModeException(
            code = errorCode,
            message = this.message ?: DevModeException.ERROR_MSG_UNKNOWN,
            cause = this
        )
}

fun Throwable.code(): Int {
    return if (this is DevModeException) this.code else -1
}

fun Throwable.toLog(): String {
    return if(this is DevModeException) {
        this.toLog()
    } else {
        "$message (unknown)"
    }
}