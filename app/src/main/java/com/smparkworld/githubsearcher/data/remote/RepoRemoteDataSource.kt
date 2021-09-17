package com.smparkworld.githubsearcher.data.remote

import com.smparkworld.githubsearcher.model.Repo
import com.smparkworld.githubsearcher.model.Result

interface RepoRemoteDataSource {

    suspend fun getRepoById(uid: String): Result<List<Repo>>
}