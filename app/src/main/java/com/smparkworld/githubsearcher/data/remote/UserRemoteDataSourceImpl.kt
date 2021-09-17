package com.smparkworld.githubsearcher.data.remote

import com.smparkworld.githubsearcher.data.remote.api.SearchUsersResponse
import com.smparkworld.githubsearcher.data.remote.api.GithubAPI
import com.smparkworld.githubsearcher.model.Result
import com.smparkworld.githubsearcher.model.Result.Success
import com.smparkworld.githubsearcher.model.Result.Error
import com.smparkworld.githubsearcher.model.User
import retrofit2.HttpException
import javax.inject.Inject

class UserRemoteDataSourceImpl @Inject constructor(
        private val githubAPI: GithubAPI
) : UserRemoteDataSource {

    override suspend fun searchById(uid: String, size:Int, page: Int): Result<SearchUsersResponse> {
        return try {
            val response = githubAPI.searchUsersById(uid, size, page)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Success(body)
                } else {
                    throw NullPointerException("[GithubAPI] searchUsersById API response body is null.")
                }
            } else {
                throw HttpException(response)
            }
        } catch (e: Exception) {
            Error(e)
        }
    }

    override suspend fun getById(uid: String): Result<User> {
        return try {
            val response = githubAPI.getUserById(uid)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Success(body)
                } else {
                    throw NullPointerException("[GithubAPI] findUserById API response body is null.")
                }
            } else {
                throw HttpException(response)
            }
        } catch (e: Exception) {
            Error(e)
        }
    }
}