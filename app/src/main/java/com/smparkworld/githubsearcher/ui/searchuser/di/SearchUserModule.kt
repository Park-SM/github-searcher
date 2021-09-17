package com.smparkworld.githubsearcher.ui.searchuser.di

import androidx.lifecycle.ViewModel
import com.smparkworld.githubsearcher.di.ViewModelKey
import com.smparkworld.githubsearcher.ui.searchuser.SearchUserViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class SearchUserModule {

    @Binds
    @IntoMap
    @ViewModelKey(SearchUserViewModel::class)
    abstract fun bindSearchUserViewModel(searchUserViewModel: SearchUserViewModel): ViewModel
}