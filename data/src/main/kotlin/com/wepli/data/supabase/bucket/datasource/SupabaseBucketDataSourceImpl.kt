package com.wepli.data.supabase.bucket.datasource

import android.util.Log
import com.wepli.core.common.BuildConfig
import com.wepli.core.kotlin.flow.FlowResult
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.storage.FileUploadResponse
import io.github.jan.supabase.storage.storage
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SupabaseBucketDataSourceImpl @Inject constructor(
    private val supabase: SupabaseClient
): SupabaseBucketDataSource {

    override fun updateFile(bucketName: String, file: ByteArray): FlowResult<FileUploadResponse> = flow {
        val fileUploadResult = runCatching {
            supabase.storage[bucketName]
                .upload(
                    path = "path/to/file",
                    data = file,
                    options = { upsert = true }
                )
        }.map {
            it.copy(path = buildBucketImagePath(bucketName, it.path),)
        }.onFailure {
            Log.e("SupabaseBucketDataSource", "File upload failed", it)
        }

        emit(fileUploadResult)
    }

    private fun buildBucketImagePath(bucketName: String, imagePath: String): String {
        return "${BuildConfig.SUPABASE_URL}/storage/v1/object/public/${bucketName}/${imagePath}"
    }
}