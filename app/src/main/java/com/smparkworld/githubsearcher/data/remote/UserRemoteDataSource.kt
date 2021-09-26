package com.smparkworld.githubsearcher.data.remote

import com.smparkworld.githubsearcher.data.remote.api.SearchUsersResponse
import com.smparkworld.githubsearcher.model.User
import io.reactivex.rxjava3.core.Single

interface UserRemoteDataSource {

    fun searchById(uid: String, size: Int, page: Int): Single<SearchUsersResponse>

    fun getById(uid: String): Single<User>
}