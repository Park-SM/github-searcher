package com.smparkworld.githubsearcher.di

import android.content.Context
import com.smparkworld.githubsearcher.ui.detailuser.di.DetailUserComponent
import com.smparkworld.githubsearcher.ui.searchuser.di.SearchUserComponent
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AppModule::class,
    AppModuleBinds::class,
    NetworkModule::class
])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): AppComponent
    }

    fun searchUserComponent(): SearchUserComponent.Factory
    fun detailUserComponent(): DetailUserComponent.Factory
}