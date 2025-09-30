package com.wepli.app.di

import android.content.Context
import android.content.Intent
import com.wepli.app.MainActivity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object PendingIntentModule {

    @Provides
    fun bindMainActivityIntent(@ApplicationContext context: Context): Intent {
        return Intent(context, MainActivity::class.java)
    }
}