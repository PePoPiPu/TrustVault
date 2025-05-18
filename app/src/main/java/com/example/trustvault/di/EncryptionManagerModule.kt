package com.example.trustvault.di

import com.example.trustvault.data.encryption.EncryptionManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object EncryptionManagerModule {
    @Provides
    fun provideEncryptionManager(): EncryptionManager {
        return EncryptionManager
    }
}