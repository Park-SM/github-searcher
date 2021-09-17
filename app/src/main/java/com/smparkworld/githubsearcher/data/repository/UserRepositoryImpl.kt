package com.smparkworld.githubsearcher.data.repository

import androidx.paging.*
import com.smparkworld.githubsearcher.data.remote.UserRemoteDataSource
import com.smparkworld.githubsearcher.model.UserModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val remoteDataSource: UserRemoteDataSource
) : UserRepository {

    override suspend fun searchUserById(uid: String, pageSize: Int) =
        Pager(PagingConfig(pageSize = pageSize)) {
            UserPagingSource(remoteDataSource, uid, pageSize)
        }.flow.map {
            it.map { item -> UserModel.Item(item) }
              .insertSeparators { before, after ->
                  if (before is UserModel.Item && after is UserModel.Item) UserModel.Separator else null
              }
        }
}