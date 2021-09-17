package com.smparkworld.githubsearcher.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.smparkworld.githubsearcher.data.remote.UserRemoteDataSource
import com.smparkworld.githubsearcher.model.Result.Success
import com.smparkworld.githubsearcher.model.Result.Error
import com.smparkworld.githubsearcher.model.User
import java.lang.Exception

class UserPagingSource(
    private val remoteDataSource: UserRemoteDataSource,
    private val query: String,
    private val pageSize: Int
) : PagingSource<Int, User>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        try {
            val nextPage = params.key ?: 1

            val result = remoteDataSource.searchById(query, pageSize, nextPage)
            if (result is Success) {

                val isAvailable = result.data.totalCount > pageSize * nextPage
                return LoadResult.Page(
                    data = result.data.items,
                    prevKey = null,
                    nextKey = if (isAvailable) nextPage + 1 else null
                )
            } else {
                throw (result as Error).e
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}