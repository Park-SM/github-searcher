package com.smparkworld.githubsearcher.di

import androidx.lifecycle.ViewModelProvider
import com.smparkworld.githubsearcher.data.remote.UserRemoteDataSource
import com.smparkworld.githubsearcher.data.remote.UserRemoteDataSourceImpl
import com.smparkworld.githubsearcher.data.repository.UserRepository
import com.smparkworld.githubsearcher.data.repository.UserRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
abstract class AppModuleBinds {

    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    abstract fun bindUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository

    @Binds
    abstract fun bindUserRemoteDataSource(userRemoteDataSourceImpl: UserRemoteDataSourceImpl): UserRemoteDataSource
}