package common

class WePLiException(
    val code: Int = -1,
    override val message: String? = ERROR_MSG_UNKNOWN,
    override val cause: Throwable? = null
) : Throwable(message) {

    fun toLog() = "$message (${code})"

    companion object {
        const val ERROR_MSG_UNKNOWN = "알 수 없는 에러가 발생하였습니다."
    }
}

fun Throwable.toWePLiException(errorCode: Int = -1): WePLiException {
    return if (this is WePLiException) {
        this
    } else {
        WePLiException(
            code = errorCode,
            message = this.message ?: WePLiException.ERROR_MSG_UNKNOWN,
            cause = this
        )
    }
}

fun Throwable.code(): Int {
    return if (this is WePLiException) this.code else -1
}

fun Throwable.toLog(): String {
    return if(this is WePLiException) {
        this.toLog()
    } else {
        "$message (unknown)"
    }
}