package com.smparkworld.githubsearcher.ui.searchuser

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import com.smparkworld.githubsearcher.R
import com.smparkworld.githubsearcher.data.repository.UserRepository
import com.smparkworld.githubsearcher.model.UserModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

class SearchUserViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _error = MutableLiveData<Int>()
    val error: LiveData<Int> = _error

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _isEmpty = MutableLiveData<Boolean>()
    val isEmpty: LiveData<Boolean> = _isEmpty

    private val _users = MutableLiveData<Flow<PagingData<UserModel>>>()
    val users: LiveData<Flow<PagingData<UserModel>>> = _users

    val searchId = MutableLiveData<String>()

    fun search() {
        if (searchId.value.isNullOrBlank()) {
            _error.value = R.string.activitySearchUser_searchEmpty
            return
        }
        val search = searchId.value!!
        viewModelScope.launch {
            _users.value = userRepository.searchUserById(search, 50)
        }
    }

    fun setUsersLoadState(loadState: CombinedLoadStates?) {
        val state = loadState?.refresh
        _loading.value = state is LoadState.Loading
        _isEmpty.value = state is LoadState.Error || state == null

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
}