package common

class WePLiException(
    val code: Int = -1,
    override val message: String? = "알 수 없는 에러가 발생하였습니다.",
    override val cause: Throwable? = null
) : Throwable(message) {

    fun toLog() = "$message (${code})"
}

fun Throwable.toLog(): String {
    return if(this is WePLiException) {
        this.toLog()
    } else {
        "$message (unknown)"
    }
}