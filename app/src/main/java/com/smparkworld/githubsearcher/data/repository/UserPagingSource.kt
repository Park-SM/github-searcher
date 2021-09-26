package com.smparkworld.githubsearcher.data.repository

import androidx.paging.PagingState
import androidx.paging.rxjava3.RxPagingSource
import com.smparkworld.githubsearcher.data.remote.UserRemoteDataSource
import com.smparkworld.githubsearcher.model.User
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class UserPagingSource(
    private val remoteDataSource: UserRemoteDataSource,
    private val query: String,
    private val pageSize: Int
) : RxPagingSource<Int, User>() {

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, User>> {
        val nextPage = params.key ?: 1

        return remoteDataSource.searchById(query, pageSize, nextPage)
            .subscribeOn(Schedulers.io())
            .map<LoadResult<Int, User>> {
                val isAvailable = it.totalCount > pageSize * nextPage
                LoadResult.Page(
                    data = it.items,
                    prevKey = null,
                    nextKey = if (isAvailable) nextPage + 1 else null
                )
            }
            .onErrorReturn { LoadResult.Error(it) }
    }

    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}