package com.smparkworld.githubsearcher.data.repository

import com.smparkworld.githubsearcher.model.User
import io.reactivex.rxjava3.core.Single

interface UserRepository {

    fun searchUserById(uid: String, pageSize: Int): UserPagingSource

    fun getOverviewById(uid: String, repoLimit: Int): Single<User>
}