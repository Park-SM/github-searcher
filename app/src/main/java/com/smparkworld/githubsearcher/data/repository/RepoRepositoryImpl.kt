package com.smparkworld.githubsearcher.data.repository

import com.smparkworld.githubsearcher.data.remote.RepoRemoteDataSource
import javax.inject.Inject

class RepoRepositoryImpl @Inject constructor(
    private val remoteDataSource: RepoRemoteDataSource
) : RepoRepository {

    override suspend fun getRepoById(uid: String) =
            remoteDataSource.getRepoById(uid)
}