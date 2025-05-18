package com.wepli.data.supabase.bucket.repository

import com.wepli.core.kotlin.flow.FlowResult
import com.wepli.data.di.qualifier.SupabaseDataSource
import com.wepli.data.network.toEntityResult
import com.wepli.data.supabase.bucket.datasource.SupabaseBucketDataSource
import com.wepli.data.supabase.bucket.mapper.toEntity
import model.supabase.FileUploadResult
import javax.inject.Inject

class SupabaseBucketRepositoryImpl @Inject constructor(
    @SupabaseDataSource private val supabaseBucketDataSource: SupabaseBucketDataSource
) : SupabaseBucketRepository {

    override fun uploadFile(bucketName: String, file: ByteArray): FlowResult<FileUploadResult> {
        return supabaseBucketDataSource.updateFile(bucketName, file).toEntityResult {
            it.toEntity()
        }
    }
}