package com.wepli.core.kotlin

import kotlinx.coroutines.flow.onEach

suspend fun <T> FlowResult<T>.collectResult(
    onSuccess: ((T) -> Unit)? = null,
    onFailure: ((Throwable) -> Unit)? = null
) {
    collect { result ->
        result.fold(onSuccess ?: {}, onFailure ?: {})
    }
}

suspend fun <T> FlowResult<T>.suspendCollectResult(
    onSuccess: (suspend (T) -> Unit)? = null,
    onFailure: (suspend (Throwable) -> Unit)? = null,
) {
    suspend fun <T> Result<T>.suspendFold(
        onSuccess: suspend (T) -> Unit,
        onFailure: suspend (Throwable) -> Unit
    ) {
        exceptionOrNull()?.let { exception ->
            onFailure(exception)
        } ?: onSuccess(getOrThrow())
    }

    collect { result ->
        result.suspendFold(onSuccess ?: {}, onFailure ?: {})
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