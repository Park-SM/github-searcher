package com.smparkworld.githubsearcher.ui.searchuser

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.LoadStates
import com.smparkworld.githubsearcher.data.remote.UserRemoteDataSource
import com.smparkworld.githubsearcher.data.remote.api.SearchUsersResponse
import com.smparkworld.githubsearcher.data.repository.UserPagingSource
import com.smparkworld.githubsearcher.data.repository.UserRepository
import com.smparkworld.githubsearcher.extension.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import com.smparkworld.githubsearcher.BuildConfig
import com.smparkworld.githubsearcher.R
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import java.net.UnknownHostException

class SearchUserViewModelTest {

    @get:Rule
    var rule = InstantTaskExecutorRule()

    private lateinit var searchUserViewModel: SearchUserViewModel

    @Before
    fun setUp() {
        val remoteDataSource  = Mockito.mock(UserRemoteDataSource::class.java)
        val userPagingSource = UserPagingSource(remoteDataSource, "Park-SM", 50)
        val repository = Mockito.mock(UserRepository::class.java)
        val response = Single.just(SearchUsersResponse(50, listOf()))

        Mockito.`when`(remoteDataSource.searchById("Park-SM", 50, 1)).thenReturn(response)
        Mockito.`when`(repository.searchUserById("Park-SM", 50)).thenReturn(userPagingSource)

        searchUserViewModel = SearchUserViewModel(repository)
    }

    @Test
    fun `search_when setting Uid_then should set users`() {

        searchUserViewModel.searchId.value = "Park-SM"
        searchUserViewModel.search()

        assertThat(searchUserViewModel.users.getOrAwaitValue()).isNotNull()
    }

    @Test
    fun `setUsersLoadState_when setting null_then should set state LiveData`() {

        searchUserViewModel.setUsersLoadState(null)

        assertThat(searchUserViewModel.isEmpty.getOrAwaitValue()).isTrue()
        assertThat(searchUserViewModel.loading.getOrAwaitValue()).isFalse()
    }

    @Test
    fun `setUsersLoadState_when setting loading loadState instance_then should set state`() {

        val loadState = LoadStates(
            refresh = LoadState.Loading,
            prepend = LoadState.NotLoading(true),
            append  = LoadState.NotLoading(true)
        ).run {
            CombinedLoadStates(
                refresh = refresh,
                prepend = prepend,
                append  = append,
                source  = this
            )
        }
        searchUserViewModel.setUsersLoadState(loadState)

        assertThat(searchUserViewModel.isEmpty.getOrAwaitValue()).isFalse()
        assertThat(searchUserViewModel.loading.getOrAwaitValue()).isTrue()
    }

    @Test
    fun `setUsersLoadState_when setting error loadState instance_then should set state`() {

        val loadState = LoadStates(
            refresh = LoadState.Error(Exception("Test Exception")),
            prepend = LoadState.NotLoading(true),
            append  = LoadState.NotLoading(true)
        ).run {
            CombinedLoadStates(
                refresh = refresh,
                prepend = prepend,
                append  = append,
                source  = this
            )
        }
        searchUserViewModel.setUsersLoadState(loadState)

        assertThat(searchUserViewModel.isEmpty.getOrAwaitValue()).isTrue()
        assertThat(searchUserViewModel.loading.getOrAwaitValue()).isFalse()
        assertThat(searchUserViewModel.error.getOrAwaitValue()).isEqualTo(R.string.error_fatalNetwork)
    }

    @Test
    fun `setUsersLoadState_when networking fail_then should set error msg`() {

        val loadState = LoadStates(
            refresh = LoadState.Error(
                UnknownHostException("Unable to resolve host \"${BuildConfig.SERVER_URL}\": No address associated with hostname")
            ),
            prepend = LoadState.NotLoading(true),
            append  = LoadState.NotLoading(true)
        ).run {
            CombinedLoadStates(
                refresh = refresh,
                prepend = prepend,
                append  = append,
                source  = this
            )
        }
        searchUserViewModel.setUsersLoadState(loadState)

        assertThat(searchUserViewModel.isEmpty.getOrAwaitValue()).isTrue()
        assertThat(searchUserViewModel.loading.getOrAwaitValue()).isFalse()
        assertThat(searchUserViewModel.error.getOrAwaitValue()).isEqualTo(R.string.error_failedToConnectNetwork)
    }
}