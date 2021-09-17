package com.smparkworld.githubsearcher.ui.detailuser.di

import com.smparkworld.githubsearcher.ui.detailuser.DetailUserActivity
import com.smparkworld.githubsearcher.ui.detailuser.EventFragment
import com.smparkworld.githubsearcher.ui.detailuser.RepoFragment
import com.smparkworld.githubsearcher.ui.detailuser.UserInfoFragment
import dagger.Subcomponent

@Subcomponent(modules = [DetailUserModule::class])
interface DetailUserComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): DetailUserComponent
    }

    fun inject(detailUserActivity: DetailUserActivity)
    fun inject(userInfoFragment: UserInfoFragment)
    fun inject(repoFragment: RepoFragment)
    fun inject(eventFragment: EventFragment)
}