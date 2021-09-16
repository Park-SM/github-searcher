package com.smparkworld.githubsearcher.data.repository

import androidx.paging.PagingData
import com.smparkworld.githubsearcher.model.Result
import com.smparkworld.githubsearcher.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun searchUserById(uid: String): Result<Flow<PagingData<User>>>
}