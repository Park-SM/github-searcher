package com.smparkworld.githubsearcher.data.remote

import com.smparkworld.githubsearcher.data.remote.api.RepoAPI
import com.smparkworld.githubsearcher.model.Repo
import com.smparkworld.githubsearcher.model.Result
import retrofit2.HttpException
import java.lang.Integer.min
import javax.inject.Inject

class RepoRemoteDataSourceImpl @Inject constructor(
        private val repoAPI: RepoAPI
) : RepoRemoteDataSource {

    override suspend fun getRepoById(uid: String): Result<List<Repo>> {
        return try {
            val response = repoAPI.getById(uid)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.Success(body.slice(0..2.coerceAtMost(body.size - 1)))
                } else {
                    throw NullPointerException("[RepoAPI] getById API response body is null.")
                }
            } else {
                throw HttpException(response)
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}