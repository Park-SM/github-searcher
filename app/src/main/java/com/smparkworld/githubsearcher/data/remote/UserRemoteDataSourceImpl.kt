package com.smparkworld.githubsearcher.data.remote

import com.smparkworld.githubsearcher.data.remote.api.GithubAPI
import com.smparkworld.githubsearcher.data.remote.api.SearchUsersResponse
import com.smparkworld.githubsearcher.model.User
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class UserRemoteDataSourceImpl @Inject constructor(
        private val githubAPI: GithubAPI
) : UserRemoteDataSource {

    override fun searchById(uid: String, size:Int, page: Int): Single<SearchUsersResponse> =
        githubAPI.searchUsersById(uid, size, page)
            .subscribeOn(Schedulers.io())

    override fun getById(uid: String): Single<User> =
        githubAPI.getUserById(uid)
            .subscribeOn(Schedulers.io())
}