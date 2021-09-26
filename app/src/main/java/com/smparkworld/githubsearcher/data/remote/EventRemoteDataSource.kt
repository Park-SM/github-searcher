package com.smparkworld.githubsearcher.data.remote

import com.smparkworld.githubsearcher.model.Event
import io.reactivex.rxjava3.core.Single

interface EventRemoteDataSource {

    fun getById(uid: String, size: Int, page: Int): Single<List<Event>>
}