package com.example.codechallangebankapp.di

import com.example.codechallangebankapp.data.local.AuthPreferences
import com.example.codechallangebankapp.data.remote.services.ApiService
import com.example.codechallangebankapp.data.repositories.AccountsRepositoryImpl
import com.example.codechallangebankapp.data.repositories.LoginRepositoryImpl
import com.example.codechallangebankapp.domain.repositories.AccountsRepository
import com.example.codechallangebankapp.domain.repositories.LoginRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoriesModule {

    @Provides
    @Singleton
    fun providesLoginRepository(apiService:ApiService,preferences: AuthPreferences): LoginRepository {
        return LoginRepositoryImpl(apiService,preferences)
    }

    @Provides
    @Singleton
    fun providesAccountsRepository(apiService:ApiService): AccountsRepository {
        return AccountsRepositoryImpl(apiService)
    }
}