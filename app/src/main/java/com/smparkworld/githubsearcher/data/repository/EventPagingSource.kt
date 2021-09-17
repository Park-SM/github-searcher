package com.smparkworld.githubsearcher.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.smparkworld.githubsearcher.data.remote.EventRemoteDataSource
import com.smparkworld.githubsearcher.model.Event
import com.smparkworld.githubsearcher.model.Result
import java.lang.Exception

class EventPagingSource(
        private val remoteDataSource: EventRemoteDataSource,
        private val uid: String,
        private val pageSize: Int
) : PagingSource<Int, Event>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Event> {
        try {
            val nextPage = params.key ?: 1

            val result = remoteDataSource.getById(uid, pageSize, nextPage)
            if (result is Result.Success) {

                val isAvailable = result.data.isNotEmpty()
                return LoadResult.Page(
                    data = result.data,
                    prevKey = null,
                    nextKey = if (isAvailable) nextPage + 1 else null
                )
            } else {
                throw (result as Result.Error).e
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Event>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}