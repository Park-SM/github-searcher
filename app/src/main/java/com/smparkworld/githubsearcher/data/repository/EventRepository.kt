package com.smparkworld.githubsearcher.data.repository

interface EventRepository {

    fun getEventsById(uid: String, pageSize: Int): EventPagingSource
}