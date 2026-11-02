package com.timcoumu.matrix.feature.auth.di

import com.timcoumu.core.data.AppDataStore
import com.timcoumu.matrix.feature.auth.data.RepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalDataStoreModule {
    @Provides
    @Singleton
    fun provideRepository( dataStore: AppDataStore): RepositoryImpl{
        return RepositoryImpl(dataStore)
    }
}