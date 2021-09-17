package com.smparkworld.githubsearcher.ui.detailuser.di

import com.smparkworld.githubsearcher.ui.detailuser.DetailUserActivity
import dagger.Subcomponent

@Subcomponent(modules = [DetailUserModule::class])
interface DetailUserComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): DetailUserComponent
    }

    fun inject(detailUserActivity: DetailUserActivity)
}