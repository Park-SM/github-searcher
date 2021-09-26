package com.smparkworld.githubsearcher.data.repository

import com.smparkworld.githubsearcher.data.remote.UserRemoteDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val remoteDataSource: UserRemoteDataSource,
) : UserRepository {

    override fun searchUserById(uid: String, pageSize: Int) =
            UserPagingSource(remoteDataSource, uid, pageSize)

    override fun getUserById(uid: String) =
            remoteDataSource.getById(uid)
}