package com.wepli.data.supabase.bucket.datasource

import com.wepli.core.kotlin.flow.FlowResult
import io.github.jan.supabase.storage.FileUploadResponse

interface SupabaseBucketDataSource {

    fun updateFile(bucketName: String, file: ByteArray, extension: String): FlowResult<FileUploadResponse>
}