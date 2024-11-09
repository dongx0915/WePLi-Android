package extensions

import android.util.Log
import com.google.gson.Gson

/**
 * 클래스를 JSON으로 변환
 */
inline fun <reified T> T.toJsonString(): String {
    return runCatching {
        Gson().toJson(this).orEmpty().also { json ->
            Log.i("${T::class.java.simpleName}ToJson", json)
        }
    }.getOrDefault("")
}

/**
 * Json을 클래스로 변환
 */
inline fun <reified T> String.parseFromJson(): T? {
    return runCatching {
        Gson().fromJson(this, T::class.java).also { parsedObject ->
            Log.i("ParseFromJson", parsedObject.toString())
        }
    }.getOrNull()
}

/**
 * Json을 클래스로 변환
 * @param default 실패 시 반환할 기본값
 */
inline fun <reified T> String.parseFromJson(default: T): T {
    return runCatching {
        Gson().fromJson(this, T::class.java).also { parsedObject ->
            Log.i("ParseFromJson", parsedObject.toString())
        }
    }.getOrDefault(default)
}