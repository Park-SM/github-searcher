package com.smparkworld.githubsearcher.data.repository

import androidx.paging.PagingState
import androidx.paging.rxjava3.RxPagingSource
import com.smparkworld.githubsearcher.data.remote.EventRemoteDataSource
import com.smparkworld.githubsearcher.model.Event
import io.reactivex.rxjava3.core.Single

class EventPagingSource(
        private val remoteDataSource: EventRemoteDataSource,
        private val uid: String,
        private val pageSize: Int
) : RxPagingSource<Int, Event>() {

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, Event>> {
        val nextPage = params.key ?: 1

        return remoteDataSource.getById(uid, pageSize, nextPage)
                .map<LoadResult<Int, Event>> { list ->
                    val isAvailable = list.isNotEmpty()
                    LoadResult.Page(
                        data = list,
                        prevKey = null,
                        nextKey = if (isAvailable) nextPage + 1 else null
                    )
                }
                .onErrorReturn { LoadResult.Error(it) }
    }

    override fun getRefreshKey(state: PagingState<Int, Event>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}