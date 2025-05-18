package com.wepli.data.supabase.bucket.repository

import com.wepli.core.kotlin.flow.FlowResult
import model.supabase.FileUploadResult

interface SupabaseBucketRepository {

    fun uploadFile(
        bucketName: String,
        file: ByteArray
    ): FlowResult<FileUploadResult>
}