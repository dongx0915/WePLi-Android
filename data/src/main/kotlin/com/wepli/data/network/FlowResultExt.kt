package com.wepli.data.network

import common.FlowResult
import common.WePLiException
import kotlinx.coroutines.flow.map

fun <R, D> FlowResult<R>.toEntityResult(mapper: (R) -> D): FlowResult<D> {
    fun <R, D> Result<R>.parseResult(mapper: (R) -> D): Result<D> = when {
        this.isSuccess -> Result.success(
            // CallAdapter에서 body가 null인 경우도 걸러주고 있으므로
            // Result.success의 데이터가 null인 경우는 없을듯함
            mapper(this.getOrNull()!!)
        )
        else -> Result.failure(
            this.exceptionOrNull() ?: WePLiException()
        )
    }

    return map {
        it.parseResult(mapper)
    }
}