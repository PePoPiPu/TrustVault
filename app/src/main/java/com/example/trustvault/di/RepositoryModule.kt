package com.example.trustvault.di

import com.example.trustvault.data.repositories.SMSRepositoryImpl
import com.example.trustvault.data.repositories.StoredAccountRepositoryImpl
import com.example.trustvault.data.repositories.UserRepositoryImpl
import com.example.trustvault.domain.repositories.SMSRepository
import com.example.trustvault.domain.repositories.StoredAccountRepository
import com.example.trustvault.domain.repositories.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)  // This will make it available for the entire app
abstract class RepositoryModule {
    // The @Binds annotation tells Hilt how to provide an implementation for an interface.
    @Binds
    abstract fun bindUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository
    @Binds
    abstract fun bindSmsRepository(smsRepositoryImpl: SMSRepositoryImpl) : SMSRepository
    @Binds
    abstract fun bindStoredAccountRepository(storedAccountRepositoryImpl: StoredAccountRepositoryImpl) : StoredAccountRepository
}