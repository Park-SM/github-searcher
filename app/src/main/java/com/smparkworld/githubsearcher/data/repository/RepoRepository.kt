package com.smparkworld.githubsearcher.data.repository

import com.smparkworld.githubsearcher.model.Repo
import com.smparkworld.githubsearcher.model.Result

interface RepoRepository {

    suspend fun getRepoById(uid: String): Result<List<Repo>>
}