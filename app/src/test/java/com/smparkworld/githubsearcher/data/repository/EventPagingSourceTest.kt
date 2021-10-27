package com.smparkworld.githubsearcher.data.repository

import androidx.paging.PagingSource.LoadParams
import androidx.paging.PagingSource.LoadResult
import com.smparkworld.githubsearcher.data.remote.EventRemoteDataSource
import com.smparkworld.githubsearcher.model.Event
import com.smparkworld.githubsearcher.model.EventTargetRepo
import com.smparkworld.githubsearcher.model.User
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Test

import org.mockito.Mockito

class EventPagingSourceTest {

    private val OPT_PAEG_SIZE = 5
    private val OPT_PAGE_MAX = 2
    private val OPT_MAX_COUNT = OPT_PAGE_MAX * OPT_PAEG_SIZE

    private val source by lazy {
        mutableListOf<Event>().apply {
            for (i in 0 until OPT_MAX_COUNT) {
                val e = Event(
                    "${i + 20000000}", "PushEvent",
                    User("Park-SM", null, "https://avatars.githubusercontent.com/u/47319426?", null, null),
                    EventTargetRepo("Park-SM/github-searcher", "https://api.github.com/repos/Park-SM/github-searcher")
                )
                add(e)
            }
        }
    }

    private lateinit var pagingSource: EventPagingSource

    @Before
    fun setUp() {
        val remoteDataSource = Mockito.mock(EventRemoteDataSource::class.java)
        Mockito.`when`(remoteDataSource.getById("Park-SM", OPT_PAEG_SIZE, 1))
               .thenReturn(Single.just(source.subList(0, 5)))
        Mockito.`when`(remoteDataSource.getById("Park-SM", OPT_PAEG_SIZE, 2))
               .thenReturn(Single.just(source.subList(5, 10)))
        Mockito.`when`(remoteDataSource.getById("Park-SM", OPT_PAEG_SIZE, 3))
                .thenReturn(Single.just(emptyList()))

        pagingSource = EventPagingSource(remoteDataSource, "Park-SM", OPT_PAEG_SIZE)
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
        val expected = LoadResult.Page(
            data = source.subList(5, 10),
            prevKey = null,
            nextKey = 3
        )
        assertLoadSingle(appendParams, expected)
    }

    @Test
    fun `loadSingle_when an empty list_then should return a page that nextKey is null`() {

        val appendParams = LoadParams.Append(3, OPT_PAEG_SIZE, false)
        val expected = LoadResult.Page<Int, Event>(
            data = emptyList(),
            prevKey = null,
            nextKey = null
        )
        assertLoadSingle(appendParams, expected)
    }

    private fun assertLoadSingle(params: LoadParams<Int>, expected: LoadResult.Page<Int, Event>) =
            pagingSource.loadSingle(params)
                    .test()
                    .await()
                    .assertComplete()
                    .assertNoErrors()
                    .assertValueCount(1)
                    .assertValue(expected)
}