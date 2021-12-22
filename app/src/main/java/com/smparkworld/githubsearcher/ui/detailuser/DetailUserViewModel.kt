package com.smparkworld.githubsearcher.ui.detailuser

import androidx.lifecycle.*
import androidx.paging.*
import com.smparkworld.githubsearcher.R
import com.smparkworld.githubsearcher.data.repository.EventRepository
import com.smparkworld.githubsearcher.data.repository.UserRepository
import com.smparkworld.githubsearcher.model.*
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class DetailUserViewModel @Inject constructor(
        private val userRepository: UserRepository,
        private val eventRepository: EventRepository
) : ViewModel() {

    private val _error = MutableLiveData<Int>()
    val error: LiveData<Int> = _error

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _events = MutableLiveData<Flow<PagingData<DetailUserUiModel>>>()
    val events: LiveData<Flow<PagingData<DetailUserUiModel>>> = _events

    private val _isEmpty = MutableLiveData<Boolean>()
    val isEmpty: LiveData<Boolean> = _isEmpty

    private var uid: String? = null

    @Suppress("UNCHECKED_CAST")
    fun loadUser(uid: String) {
        _loading.value = true

        this.uid = uid
        userRepository.getOverviewById(uid, 3)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally { _loading.value = false }
            .subscribe(
                { user ->
                    _events.value = Pager(
                        PagingConfig(pageSize = 50)
                    ) {
                        eventRepository.getEventsById(uid, 50)
                    }.flow.map {
                        it.map { item -> DetailUserUiModel.Item(item) as DetailUserUiModel}
                            .insertHeaderItem(item = DetailUserUiModel.Header(user))
                            .insertSeparators { before, after ->
                                if (before is DetailUserUiModel.Item && after is DetailUserUiModel.Item) {
                                    DetailUserUiModel.Separator
                                } else {
                                    null
                                }
                            }
                    }
                },
                { error ->
                    _error.value = R.string.error_failedToConnectNetwork
                    error.printStackTrace()
                }
            )
    }

    fun setEventEmpty(loadState: CombinedLoadStates?) {
        val state = loadState?.refresh
        _loading.value = state is LoadState.Loading
        _isEmpty.value = state == null

        if (state is LoadState.Error) {
            _error.value = when (state.error) {
                is IOException ->
                    R.string.error_failedToConnectNetwork
                else ->
                    R.string.error_fatalNetwork
            }
            state.error.printStackTrace()
        }
    }

    fun refresh() {
        uid?.let { loadUser(it) }
    }

}