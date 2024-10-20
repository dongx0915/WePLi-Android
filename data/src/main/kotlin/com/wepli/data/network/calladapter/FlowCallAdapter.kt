package com.wepli.data.network.calladapter

import com.wepli.data.base.ErrorResponse
import com.wepli.core.kotlin.FlowResult
import common.WePLiException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.serialization.json.Json
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class FlowCallAdapter<T>(
    private val responseType: Type
) : CallAdapter<T, FlowResult<T>> {

    private val json = Json { ignoreUnknownKeys = true }
    override fun responseType() = responseType

    // Retrofit의 Call을 Result<>로 변환
    override fun adapt(call: Call<T>): FlowResult<T> = flow {
        emit(flowApiCall(call))
    }.flowOn(Dispatchers.IO)

    private suspend fun flowApiCall(call: Call<T>): Result<T> {
        return suspendCancellableCoroutine { continuation ->
            call.enqueue(object : Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    continuation.resume(parseResponse(response))
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                    continuation.resumeWithException(t)
                }
            })

            continuation.invokeOnCancellation {
                call.cancel()
            }
        }
    }

    private fun parseResponse(response: Response<T>): Result<T> {
        val nullBodyException by lazy {
            WePLiException(response.code(), ERROR_MSG_RESPONSE_IS_NULL)
        }

        if (!response.isSuccessful) {
            return Result.failure(parseErrorResponse(response))
        }

        return response.body()?.let {
            Result.success(it)
        } ?: Result.failure(nullBodyException)
    }

    // Response에서 오류를 파싱하여 RunnectException 객체를 생성
    private fun parseErrorResponse(response: Response<*>): WePLiException {
        val errorBodyString = response.errorBody()?.string()
        val errorResponse = errorBodyString?.let { json.decodeFromString<ErrorResponse>(it) }

        val errorMessage = errorResponse?.message ?: ERROR_MSG_COMMON
        return WePLiException(response.code(), errorMessage)
    }

    companion object {
        private const val ERROR_MSG_COMMON = "알 수 없는 에러가 발생하였습니다."
        private const val ERROR_MSG_RESPONSE_IS_NULL = "데이터를 불러올 수 없습니다."
    }
}