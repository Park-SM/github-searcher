package com.smparkworld.githubsearcher.data.repository

import com.smparkworld.githubsearcher.data.remote.UserRemoteDataSource
import com.smparkworld.githubsearcher.model.User
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val remoteDataSource: UserRemoteDataSource,
) : UserRepository {

    override fun searchUserById(uid: String, pageSize: Int) =
            UserPagingSource(remoteDataSource, uid, pageSize)

    override fun getOverviewById(uid: String, repoLimit: Int) =
            remoteDataSource.getOverviewById(uid, repoLimit)

}