package com.smparkworld.githubsearcher.data.remote

import com.smparkworld.githubsearcher.model.Repo
import io.reactivex.rxjava3.core.Single

interface RepoRemoteDataSource {

    fun getRepoById(uid: String): Single<List<Repo>>
}