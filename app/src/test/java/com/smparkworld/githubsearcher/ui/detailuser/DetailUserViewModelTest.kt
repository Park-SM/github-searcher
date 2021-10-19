package com.smparkworld.githubsearcher.ui.detailuser

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.LoadStates
import com.google.common.truth.Truth.assertThat
import com.smparkworld.githubsearcher.BuildConfig
import com.smparkworld.githubsearcher.R
import com.smparkworld.githubsearcher.data.remote.EventRemoteDataSource
import com.smparkworld.githubsearcher.data.repository.EventPagingSource
import com.smparkworld.githubsearcher.data.repository.EventRepository
import com.smparkworld.githubsearcher.data.repository.UserRepository
import com.smparkworld.githubsearcher.extension.getOrAwaitValue
import com.smparkworld.githubsearcher.model.User
import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.After
import org.junit.Before
import org.junit.Rule

import org.junit.Test
import org.mockito.Mockito
import java.net.UnknownHostException

class DetailUserViewModelTest {

    @Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var detailUserViewModel: DetailUserViewModel

    @Before
    fun setUp() {
        val user = User("Park-SM", "SangMin Park", "test url", "Hello, world!", null)

        val eventRemoteDataSource = Mockito.mock(EventRemoteDataSource::class.java)
        val eventPagingSource = EventPagingSource(eventRemoteDataSource, "Park-SM", 50)
        val eventRepository = Mockito.mock(EventRepository::class.java)
        val userRepository = Mockito.mock(UserRepository::class.java)

        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        Mockito.`when`(userRepository.getOverviewById("Park-SM", 3)).thenReturn(Single.just(user))
        Mockito.`when`(eventRepository.getEventsById("Park-SM", 50)).thenReturn(eventPagingSource)

        detailUserViewModel = DetailUserViewModel(userRepository, eventRepository)
    }

    @After
    fun teardown() {
        RxAndroidPlugins.reset()
    }

    @Test
    fun `loadUser_when invoking_then should set events`() {

        detailUserViewModel.loadUser("Park-SM")

        assertThat(detailUserViewModel.events.getOrAwaitValue()).isNotNull()
    }

    @Test
    fun `setEventEmpty_when events is empty_then should set state`() {

        detailUserViewModel.setEventEmpty(null)

        assertThat(detailUserViewModel.loading.getOrAwaitValue()).isFalse()
        assertThat(detailUserViewModel.isEmpty.getOrAwaitValue()).isTrue()
    }

    @Test
    fun `setEventEmpty_when setting loading loadState instance_then should set state`() {

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
        detailUserViewModel.setEventEmpty(loadState)

        assertThat(detailUserViewModel.loading.getOrAwaitValue()).isTrue()
        assertThat(detailUserViewModel.isEmpty.getOrAwaitValue()).isFalse()
    }

    @Test
    fun `setEventEmpty_when setting error loadState instance_then should set state`() {

        val loadState = LoadStates(
            refresh = LoadState.Error(Exception("Test exception.")),
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
        detailUserViewModel.setEventEmpty(loadState)

        assertThat(detailUserViewModel.loading.getOrAwaitValue()).isFalse()
        assertThat(detailUserViewModel.isEmpty.getOrAwaitValue()).isFalse()
        assertThat(detailUserViewModel.error.getOrAwaitValue()).isEqualTo(R.string.error_fatalNetwork)
    }

    @Test
    fun `setEventEmpty_when networking fail_then should set error msg`() {

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
        detailUserViewModel.setEventEmpty(loadState)

        assertThat(detailUserViewModel.loading.getOrAwaitValue()).isFalse()
        assertThat(detailUserViewModel.isEmpty.getOrAwaitValue()).isFalse()
        assertThat(detailUserViewModel.error.getOrAwaitValue()).isEqualTo(R.string.error_failedToConnectNetwork)
    }

    @Test
    fun `refresh_when invoking_then should have the same effect as the loadUser function`() {

        detailUserViewModel.loadUser("Park-SM")

        assertThat(detailUserViewModel.events.getOrAwaitValue()).isNotNull()

        detailUserViewModel.refresh()

        assertThat(detailUserViewModel.events.getOrAwaitValue()).isNotNull()
    }
}