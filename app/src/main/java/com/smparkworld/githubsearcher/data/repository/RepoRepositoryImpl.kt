package com.smparkworld.githubsearcher.data.repository

import com.smparkworld.githubsearcher.data.remote.RepoRemoteDataSource
import com.smparkworld.githubsearcher.model.Repo
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class RepoRepositoryImpl @Inject constructor(
    private val remoteDataSource: RepoRemoteDataSource,
) : RepoRepository {

    override fun getRepoById(uid: String, limit: Int): Single<List<Repo>> =
            remoteDataSource.getRepoById(uid)
                .subscribeOn(Schedulers.io())
                .map { it.slice(0 until limit) }
}