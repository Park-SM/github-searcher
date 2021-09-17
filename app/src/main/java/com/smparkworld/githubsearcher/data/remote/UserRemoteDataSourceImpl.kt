package com.smparkworld.githubsearcher.data.remote

import com.smparkworld.githubsearcher.data.remote.api.SearchUsersResponse
import com.smparkworld.githubsearcher.data.remote.api.UserAPI
import com.smparkworld.githubsearcher.model.Result
import com.smparkworld.githubsearcher.model.Result.Success
import com.smparkworld.githubsearcher.model.Result.Error
import retrofit2.HttpException
import javax.inject.Inject

class UserRemoteDataSourceImpl @Inject constructor(
    private val userAPI: UserAPI
) : UserRemoteDataSource {

    // Retrofit2에서 자체적으로 스레드를 관리하기 때문에 Dispatchers.IO로 변경하지 않아도 됨.
    override suspend fun searchById(uid: String, size:Int, page: Int): Result<SearchUsersResponse> {
        return try {
            val response = userAPI.searchById(uid, size, page)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Success(body)
                } else {
                    throw NullPointerException("[UserAPI] searchById API response body is null.")
                }
            } else {
                throw HttpException(response)
            }
        } catch (e: Exception) {
            Error(e)
        }
    }
}