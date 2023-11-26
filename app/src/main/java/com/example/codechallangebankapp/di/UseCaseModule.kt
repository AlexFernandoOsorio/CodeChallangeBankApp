package com.example.codechallangebankapp.di

import com.example.codechallangebankapp.domain.repositories.AccountsRepository
import com.example.codechallangebankapp.domain.repositories.LoginRepository
import com.example.codechallangebankapp.domain.usecases.AccountsUseCase
import com.example.codechallangebankapp.domain.usecases.LoginAuthUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun providesLoginAuthUseCase(repository: LoginRepository): LoginAuthUseCase {
        return LoginAuthUseCase(repository)
    }

    @Provides
    @Singleton
    fun providesAccountsUseCase(repository: AccountsRepository): AccountsUseCase {
        return AccountsUseCase(repository)
    }
}