package com.wepli.app.di.module

import com.wepli.core.common.BuildConfig
import com.wepli.data.network.baseurl.BaseUrl
import com.wepli.devmode.network.plugin.ktor.ApiLogKtorPlugin
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.annotations.SupabaseInternal
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.serializer.KotlinXSerializer
import io.github.jan.supabase.storage.Storage
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SupabaseModule {

    @OptIn(SupabaseInternal::class)
    @Provides
    @Singleton
    fun provideSupabaseClient(
        apiLogKtorPlugin: ApiLogKtorPlugin,
    ): SupabaseClient {
        return createSupabaseClient(
            supabaseUrl = BaseUrl.SUPABASE.url,
            supabaseKey = BuildConfig.SUPABASE_KEY
        ) {
            defaultSerializer = KotlinXSerializer(
                Json {
                    ignoreUnknownKeys = true
                    prettyPrint = true
                    encodeDefaults = true
                }
            )

            install(Auth)
            install(Postgrest)
            install(Storage)

            httpConfig {
                if (BuildConfig.DEBUG) {
                    apiLogKtorPlugin.install(this)
                }
            }
        }
    }
}