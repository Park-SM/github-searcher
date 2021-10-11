package com.smparkworld.githubsearcher.ui.searchuser.di

import com.smparkworld.githubsearcher.ui.searchuser.SearchUserActivity

import dagger.Subcomponent

@Subcomponent(modules = [SearchUserModule::class])
interface SearchUserComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): SearchUserComponent
    }

    fun inject(searchUserActivity: SearchUserActivity)
}
