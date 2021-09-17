package com.smparkworld.githubsearcher.data.remote

import com.smparkworld.githubsearcher.data.remote.api.GithubAPI
import com.smparkworld.githubsearcher.model.Repo
import com.smparkworld.githubsearcher.model.Result
import retrofit2.HttpException
import javax.inject.Inject

class RepoRemoteDataSourceImpl @Inject constructor(
        private val githubAPI: GithubAPI
) : RepoRemoteDataSource {

    override suspend fun getRepoById(uid: String): Result<List<Repo>> {
        return try {
            val response = githubAPI.getReposById(uid)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.Success(body.slice(0..2.coerceAtMost(body.size - 1)))
                } else {
                    throw NullPointerException("[GithubAPI] getReposById API response body is null.")
                }
            } else {
                throw HttpException(response)
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}