package extensions

import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.InstanceCreator
import org.joda.time.Chronology
import org.joda.time.chrono.ISOChronology

object GsonProvider {

    // LocalDate 파싱을 위한 TypeAdapter
    private val chronologyInstanceCreator = InstanceCreator<Chronology> { ISOChronology.getInstance() }

    val gson: Gson = GsonBuilder()
        .registerTypeAdapter(Chronology::class.java, chronologyInstanceCreator)
        .create()
}

/**
 * 클래스를 JSON으로 변환
 */
inline fun <reified T> T.toJsonString(): String {
    return runCatching {
        GsonProvider.gson.toJson(this).orEmpty().also { json ->
            Log.i("${T::class.java.simpleName}ToJson", json)
        }
    }.onFailure {
        Log.e("${T::class.java.simpleName}ToJson", it.message.orEmpty())
    }.getOrDefault("")
}

inline fun <reified T> T.toPrettyJsonString(): String {
    return runCatching {
        GsonBuilder().setPrettyPrinting().create().toJson(this).orEmpty()
    }.getOrDefault("")
}


/**
 * Json을 클래스로 변환
 */
inline fun <reified T> String.parseFromJson(): T? {
    return runCatching {
        GsonProvider.gson.fromJson(this, T::class.java).also { parsedObject ->
            Log.i("ParseFromJson", parsedObject.toString())
        }
    }.onFailure {
        Log.e("ParseFromJson", it.message.orEmpty())
    }.getOrNull()
}

fun <T> String.parseFromJson(clazz: Class<T>): T? {
    return runCatching {
        GsonProvider.gson.fromJson(this, clazz).also { parsedObject ->
            Log.i("ParseFromJson", parsedObject.toString())
        }
    }.onFailure {
        Log.e("ParseFromJson", it.message.orEmpty())
    }.getOrNull()
}
/**
 * Json을 클래스로 변환
 * @param default 실패 시 반환할 기본값
 */
inline fun <reified T> String.parseFromJson(default: T): T {
    return runCatching {
        GsonProvider.gson.fromJson(this, T::class.java).also { parsedObject ->
            Log.i("ParseFromJson", parsedObject.toString())
        }
    }.onFailure {
        Log.e("ParseFromJson", it.message.orEmpty())
    }.getOrDefault(default)
}