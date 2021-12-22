package com.smparkworld.githubsearcher.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class DispatcherIO

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class DispatcherDefault

    @JvmStatic
    @Singleton
    @Provides
    @DispatcherIO
    fun provideIoDispatcher() = Dispatchers.IO

    @JvmStatic
    @Singleton
    @Provides
    @DispatcherDefault
    fun provideDefaultDispatcher() = Dispatchers.Default
}