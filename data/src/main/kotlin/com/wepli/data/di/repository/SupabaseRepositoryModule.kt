package com.wepli.data.di.repository

import com.wepli.data.supabase.bucket.repository.SupabaseBucketRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import model.supabase.repository.SupabaseBucketRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface SupabaseRepositoryModule {

    @Binds
    @Singleton
    fun bindSupabaseBucketRepository(supabaseBucketRepositoryImpl: SupabaseBucketRepositoryImpl): SupabaseBucketRepository
}