package com.wepli.core.kotlin.flow

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onEach

suspend fun <T> FlowResult<T>.collectResult(
    onSuccess: ((T) -> Unit)? = null,
    onFailure: ((Throwable) -> Unit)? = null
) {
    collect { result ->
        result.fold(onSuccess ?: {}, onFailure ?: {})
    }
}

suspend fun <T> FlowResult<T>.firstResult(
    onSuccess: ((T) -> Unit)? = null,
    onFailure: ((Throwable) -> Unit)? = null
) {
    first().fold(
        onSuccess = onSuccess ?: {},
        onFailure = onFailure ?: {}
    )
}

suspend fun <T> FlowResult<T>.suspendCollectResult(
    onSuccess: (suspend (T) -> Unit)? = null,
    onFailure: (suspend (Throwable) -> Unit)? = null,
) {
    collect { result ->
        result.suspendFold(onSuccess ?: {}, onFailure ?: {})
    }
}

suspend fun <T> FlowResult<T>.suspendFirstResult(
    onSuccess: (suspend (T) -> Unit)? = null,
    onFailure: (suspend (Throwable) -> Unit)? = null,
) {
    first().suspendFold(onSuccess ?: {}, onFailure ?: {})
}

private suspend fun <T> Result<T>.suspendFold(
    onSuccess: suspend (T) -> Unit,
    onFailure: suspend (Throwable) -> Unit
) {
    exceptionOrNull()?.let { exception ->
        onFailure(exception)
    } ?: onSuccess(getOrThrow())
}

fun <T> FlowResult<T>.onEachResult(
    onSuccess: ((T) -> Unit)? = null,
    onFailure: ((Throwable) -> Unit)? = null
): FlowResult<T> {
    return onEach { result ->
        result.fold(onSuccess ?: {}, onFailure ?: {})
    }
}