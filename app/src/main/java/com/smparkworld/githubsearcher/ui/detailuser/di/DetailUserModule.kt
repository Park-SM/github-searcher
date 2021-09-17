package com.smparkworld.githubsearcher.ui.detailuser.di

import androidx.lifecycle.ViewModel
import com.smparkworld.githubsearcher.di.ViewModelKey
import com.smparkworld.githubsearcher.ui.detailuser.DetailUserViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class DetailUserModule {

    @Binds
    @IntoMap
    @ViewModelKey(DetailUserViewModel::class)
    abstract fun bindDetailUserViewModel(detailUserViewModel: DetailUserViewModel): ViewModel
}