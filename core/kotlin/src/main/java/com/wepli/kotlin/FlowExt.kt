package com.wepli.kotlin

import kotlinx.coroutines.flow.onEach

suspend fun <T> FlowResult<T>.collectResult(
    onSuccess: ((T) -> Unit)? = null,
    onFailure: ((Throwable) -> Unit)? = null
) {
    collect { result ->
        result.fold(onSuccess ?: {}, onFailure ?: {})
    }
}

fun <T> FlowResult<T>.onEachResult(
    onSuccess: ((T) -> Unit)? = null,
    onFailure: ((Throwable) -> Unit)? = null
): FlowResult<T> {
    return onEach { result ->
        result.fold(onSuccess ?: {}, onFailure ?: {})
    }
}