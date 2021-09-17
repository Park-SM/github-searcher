package com.smparkworld.githubsearcher.data.repository

import androidx.paging.PagingData
import com.smparkworld.githubsearcher.model.UserModel
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun searchUserById(uid: String, pageSize: Int): Flow<PagingData<UserModel>>
}