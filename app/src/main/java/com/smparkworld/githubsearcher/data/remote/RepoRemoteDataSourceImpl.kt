package com.smparkworld.githubsearcher.data.remote

import com.smparkworld.githubsearcher.data.remote.api.GithubAPI
import com.smparkworld.githubsearcher.model.Repo
import com.smparkworld.githubsearcher.model.Result
import io.reactivex.rxjava3.core.Single
import retrofit2.HttpException
import javax.inject.Inject

class RepoRemoteDataSourceImpl @Inject constructor(
        private val githubAPI: GithubAPI
) : RepoRemoteDataSource {

    override fun getRepoById(uid: String) =
        githubAPI.getReposById(uid, "updated")
}