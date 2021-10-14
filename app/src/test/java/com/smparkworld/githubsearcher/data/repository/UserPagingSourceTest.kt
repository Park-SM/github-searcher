package com.smparkworld.githubsearcher.data.repository

import androidx.paging.PagingSource.LoadParams
import androidx.paging.PagingSource.LoadResult
import com.smparkworld.githubsearcher.data.remote.UserRemoteDataSource
import com.smparkworld.githubsearcher.data.remote.api.SearchUsersResponse
import com.smparkworld.githubsearcher.model.User
import io.reactivex.rxjava3.core.Single
import org.junit.Before

import org.junit.Test
import org.mockito.Mockito

class UserPagingSourceTest {

    private val OPT_PAEG_SIZE = 5
    private val OPT_PAGE_MAX = 2
    private val OPT_MAX_COUNT = OPT_PAGE_MAX * OPT_PAEG_SIZE

    private val source by lazy {
        mutableListOf<User>().apply {
            for (i in 0 until OPT_MAX_COUNT) {
                val user = User(
                    "Park-SM$i","SangMin Park", "https://avatars.githubusercontent.com/u/47319426?", "Hello, world!", null
                )
                add(user)
            }
        }
    }

    private lateinit var pagingSource: UserPagingSource

    @Before
    fun setUp() {
        val remoteDataSource = Mockito.mock(UserRemoteDataSource::class.java)
        Mockito.`when`(remoteDataSource.searchById("Park-SM", OPT_PAEG_SIZE, 1))
               .thenReturn(Single.just(SearchUsersResponse(OPT_MAX_COUNT, source.subList(0, 5))))
        Mockito.`when`(remoteDataSource.searchById("Park-SM", OPT_PAEG_SIZE, 2))
               .thenReturn(Single.just(SearchUsersResponse(OPT_MAX_COUNT, source.subList(5, 10))))
        Mockito.`when`(remoteDataSource.searchById("Park-SM", OPT_PAEG_SIZE, 3))
               .thenReturn(Single.just(SearchUsersResponse(OPT_MAX_COUNT, emptyList())))

        pagingSource = UserPagingSource(remoteDataSource, "Park-SM", OPT_PAEG_SIZE)
    }

    @Test
    fun `loadSingle_when first invoking_then should return a page`() {
        val refreshParams = LoadParams.Refresh<Int>(null, OPT_PAEG_SIZE, false)
        val expected = LoadResult.Page(
            data = source.subList(0, 5),
            prevKey = null,
            nextKey = 2
        )
        assertLoadSingle(refreshParams, expected)
    }

    @Test
    fun `loadSingle_when second invoking_then should return a page`() {
        val appendParams = LoadParams.Append(2, OPT_PAEG_SIZE, false)
        val expected = LoadResult.Page<Int, User>(
            data = source.subList(5, 10),
            prevKey = null,
            nextKey = null
        )
        assertLoadSingle(appendParams, expected)
    }

    @Test
    fun `loadSingle_when empty list_then should return a page that nextKey is null`() {
        val appendParams = LoadParams.Append(3, OPT_PAEG_SIZE, false)
        val expected = LoadResult.Page<Int, User>(
                data = emptyList(),
                prevKey = null,
                nextKey = null
        )
        assertLoadSingle(appendParams, expected)
    }

    private fun assertLoadSingle(params: LoadParams<Int>, expected: LoadResult.Page<Int, User>) =
            pagingSource.loadSingle(params)
                    .test()
                    .await()
                    .assertComplete()
                    .assertNoErrors()
                    .assertValueCount(1)
                    .assertValue(expected)

}