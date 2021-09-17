package com.smparkworld.githubsearcher.data.remote

import com.smparkworld.githubsearcher.data.remote.api.GithubAPI
import com.smparkworld.githubsearcher.model.Event
import com.smparkworld.githubsearcher.model.Result
import retrofit2.HttpException
import javax.inject.Inject

class EventRemoteDataSourceImpl @Inject constructor(
        private val githubAPI: GithubAPI
) : EventRemoteDataSource {

    override suspend fun getById(uid: String, size: Int, page: Int): Result<List<Event>> {
        return try {
            val response = githubAPI.getEventsById(uid, size, page)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.Success(body)
                } else {
                    throw NullPointerException("[GithubAPI] getEventsById API response body is null.")
                }
            } else {
                throw HttpException(response)
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}