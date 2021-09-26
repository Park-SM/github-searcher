package com.smparkworld.githubsearcher.data.repository

import com.smparkworld.githubsearcher.data.remote.EventRemoteDataSource
import javax.inject.Inject

class EventRepositoryImpl @Inject constructor(
        private val remoteDataSource: EventRemoteDataSource
): EventRepository {

    override fun getEventsById(uid: String, pageSize: Int) =
            EventPagingSource(remoteDataSource, uid, pageSize)
}