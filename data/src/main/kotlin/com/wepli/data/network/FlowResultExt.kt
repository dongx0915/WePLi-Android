package com.wepli.data.network

import com.wepli.core.kotlin.flow.FlowResult
import common.WePLiException
import kotlinx.coroutines.flow.map

fun <R, D> FlowResult<R>.toEntityResult(mapper: (R) -> D): FlowResult<D> {
    return map { result -> result.map(mapper) }
}