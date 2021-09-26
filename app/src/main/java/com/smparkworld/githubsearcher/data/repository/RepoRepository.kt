package com.smparkworld.githubsearcher.data.repository

import com.smparkworld.githubsearcher.model.Repo
import com.smparkworld.githubsearcher.model.Result
import io.reactivex.rxjava3.core.Single

interface RepoRepository {

    fun getRepoById(uid: String, limit: Int): Single<List<Repo>>
}