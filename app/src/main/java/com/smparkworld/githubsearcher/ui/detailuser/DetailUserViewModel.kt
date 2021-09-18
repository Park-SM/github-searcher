package com.smparkworld.githubsearcher.ui.detailuser

import androidx.lifecycle.*
import androidx.paging.PagingData
import com.smparkworld.githubsearcher.R
import com.smparkworld.githubsearcher.data.repository.EventRepository
import com.smparkworld.githubsearcher.data.repository.RepoRepository
import com.smparkworld.githubsearcher.data.repository.UserRepository
import com.smparkworld.githubsearcher.model.*
import com.smparkworld.githubsearcher.model.Result.Success
import com.smparkworld.githubsearcher.model.Result.Error
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
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

        viewModelScope.launch {
            _loading.value = true

            listOf(
                async { userRepository.getUserById(uid) },
                async { repoRepository.getRepoById(uid) }
            ).awaitAll().let { results ->
                val userResult  = results[0] as Result<User>
                val reposResult = results[1] as Result<List<Repo>>

                if (userResult is Success && reposResult is Success) {
                    val user  = userResult.data
                    val repos = reposResult.data
                    _events.value = eventRepository.getEventsById(DetailUserUIModel.Header(user, repos), uid, 50)
                } else {
                    _error.value = R.string.error_failedToConnectNetwork
                    (userResult  as? Error)?.printStackTrace()
                    (reposResult as? Error)?.printStackTrace()
                }
                _loading.value = false
            }
        }
    }

    fun setEventEmpty(isEmpty: Boolean) {
        _eventEmpty.value = isEmpty
    }

    fun refresh() {
        uid?.let { loadUser(it) }
    }

}