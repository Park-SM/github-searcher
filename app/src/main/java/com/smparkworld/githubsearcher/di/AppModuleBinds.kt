@file:Suppress("unused")

package com.smparkworld.githubsearcher.di

import com.smparkworld.githubsearcher.data.remote.*
import com.smparkworld.githubsearcher.data.repository.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModuleBinds {

    @Binds
    abstract fun bindUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository

    @Binds
    abstract fun bindUserRemoteDataSource(userRemoteDataSourceImpl: UserRemoteDataSourceImpl): UserRemoteDataSource

    @Binds
    abstract fun bindEventRepository(eventRepositoryImpl: EventRepositoryImpl): EventRepository

    @Binds
    abstract fun bindEventRemoteDataSource(eventRemoteDataSourceImpl: EventRemoteDataSourceImpl): EventRemoteDataSource
}