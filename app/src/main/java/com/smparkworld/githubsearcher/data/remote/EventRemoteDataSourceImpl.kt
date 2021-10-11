package com.smparkworld.githubsearcher.data.remote

import com.smparkworld.githubsearcher.data.remote.api.GithubAPI
import javax.inject.Inject

class EventRemoteDataSourceImpl @Inject constructor(
        private val githubAPI: GithubAPI
) : EventRemoteDataSource {

    override fun getById(uid: String, size: Int, page: Int) =
            githubAPI.getEventsById(uid, size, page)
}