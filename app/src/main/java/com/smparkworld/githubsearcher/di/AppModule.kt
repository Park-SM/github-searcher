package com.smparkworld.githubsearcher.di

import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
object AppModule {

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class DispatcherIO

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
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