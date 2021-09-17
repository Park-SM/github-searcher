package com.smparkworld.githubsearcher.data.remote

import com.smparkworld.githubsearcher.data.remote.api.SearchUsersResponse
import com.smparkworld.githubsearcher.model.Result
import com.smparkworld.githubsearcher.model.User

interface UserRemoteDataSource {

    suspend fun searchById(uid: String, size: Int, page: Int): Result<SearchUsersResponse>

    suspend fun getById(uid: String): Result<User>
}