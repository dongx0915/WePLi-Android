package com.wepli.data.di.database

import android.content.Context
import com.wepli.data.db.WePLiDatabase
import com.wepli.data.devmode.apilog.dao.ApiLogDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideWePLiDatabase(
        @ApplicationContext context: Context
    ): WePLiDatabase {
        return WePLiDatabase.create(context)
    }

    @Provides
    fun provideApiLogDao(database: WePLiDatabase): ApiLogDao {
        return database.apiLogDao()
    }
}