package com.wepli.data.supabase.bucket.mapper

import io.github.jan.supabase.storage.FileUploadResponse
import model.supabase.FileUploadResult


fun FileUploadResponse.toEntity(): FileUploadResult {
    return FileUploadResult(
        id = this.id,
        path = this.path,
        key = this.key
    )
}