package com.timcoumu.core.data.di

import android.content.Context
import com.timcoumu.core.data.AppDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    @Singleton
    fun provideLoginDataStore(@ApplicationContext context: Context): AppDataStore {
        return AppDataStore(context)
    }
}