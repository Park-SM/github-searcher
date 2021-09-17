package com.smparkworld.githubsearcher

import android.app.Application
import com.smparkworld.githubsearcher.di.DaggerAppComponent

class GithubSearcherApp : Application() {

    val appComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext)
    }
}