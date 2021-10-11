@file:Suppress("unused")

package com.smparkworld.githubsearcher.di

import androidx.lifecycle.ViewModelProvider
import com.smparkworld.githubsearcher.data.remote.*
import com.smparkworld.githubsearcher.data.repository.*
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

    @Binds
    abstract fun bindEventRepository(eventRepositoryImpl: EventRepositoryImpl): EventRepository

    @Binds
    abstract fun bindEventRemoteDataSource(eventRemoteDataSourceImpl: EventRemoteDataSourceImpl): EventRemoteDataSource
}