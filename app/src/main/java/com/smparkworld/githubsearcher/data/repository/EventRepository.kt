package com.smparkworld.githubsearcher.data.repository

import androidx.paging.PagingData
import com.smparkworld.githubsearcher.model.DetailUserUIModel
import kotlinx.coroutines.flow.Flow

interface EventRepository {

    suspend fun getEventsById(uid: String, pageSize: Int): Flow<PagingData<DetailUserUIModel>>
}