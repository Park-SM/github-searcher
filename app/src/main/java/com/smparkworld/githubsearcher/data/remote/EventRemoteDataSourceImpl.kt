package com.smparkworld.githubsearcher.data.remote

import com.smparkworld.githubsearcher.data.remote.api.GithubAPI
import com.smparkworld.githubsearcher.model.Event
import com.smparkworld.githubsearcher.model.Result
import retrofit2.HttpException
import javax.inject.Inject

class EventRemoteDataSourceImpl @Inject constructor(
        private val githubAPI: GithubAPI
) : EventRemoteDataSource {

    override fun getById(uid: String, size: Int, page: Int) =
            githubAPI.getEventsById(uid, size, page)
}