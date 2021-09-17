package com.smparkworld.githubsearcher.data.remote

import com.smparkworld.githubsearcher.model.Event
import com.smparkworld.githubsearcher.model.Result

interface EventRemoteDataSource {

    suspend fun getById(uid: String, size: Int, page: Int): Result<List<Event>>
}