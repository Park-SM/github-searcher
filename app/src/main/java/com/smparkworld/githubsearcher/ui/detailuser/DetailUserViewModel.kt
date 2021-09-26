package com.smparkworld.githubsearcher.ui.detailuser

import androidx.lifecycle.*
import androidx.paging.*
import com.smparkworld.githubsearcher.R
import com.smparkworld.githubsearcher.data.repository.EventPagingSource
import com.smparkworld.githubsearcher.data.repository.EventRepository
import com.smparkworld.githubsearcher.data.repository.RepoRepository
import com.smparkworld.githubsearcher.data.repository.UserRepository
import com.smparkworld.githubsearcher.model.*
import com.smparkworld.githubsearcher.model.Result.Success
import com.smparkworld.githubsearcher.model.Result.Error
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.Consumer
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.reactivestreams.Subscriber
import javax.inject.Inject

class DetailUserViewModel @Inject constructor(
        private val userRepository: UserRepository,
        private val repoRepository: RepoRepository,
        private val eventRepository: EventRepository
) : ViewModel() {

    private val _error = MutableLiveData<Int>()
    val error: LiveData<Int> = _error

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _events = MutableLiveData<Flow<PagingData<DetailUserUIModel>>>()
    val events: LiveData<Flow<PagingData<DetailUserUIModel>>> = _events

    private val _eventEmpty = MutableLiveData<Boolean>()
    val eventEmpty: LiveData<Boolean> = _eventEmpty

    private var uid: String? = null

    @Suppress("UNCHECKED_CAST")
    fun loadUser(uid: String) {
        this.uid = uid

        _loading.value = true
        Single.zip(
                userRepository.getUserById(uid),
                repoRepository.getRepoById(uid, 3),
                { user, repos -> DetailUserUIModel.Header(user, repos) }
            )
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally { _loading.value = false }
            .subscribe(
                { header ->
                    _events.value = Pager(
                        PagingConfig(pageSize = 50)
                    ) {
                        eventRepository.getEventsById(uid, 50)
                    }.flow.map {
                        it.map { item -> DetailUserUIModel.Item(item) as DetailUserUIModel}
                            .insertHeaderItem(item = header)
                            .insertSeparators { before, after ->
                                if (before is DetailUserUIModel.Item && after is DetailUserUIModel.Item) {
                                    DetailUserUIModel.Separator
                                } else null
                            }
                    }
                },
                { error ->
                    _error.value = R.string.error_failedToConnectNetwork
                    error.printStackTrace()
                }
            )
    }

    fun setEventEmpty(isEmpty: Boolean) {
        _eventEmpty.value = isEmpty
    }

    fun refresh() {
        uid?.let { loadUser(it) }
    }

}