package com.smparkworld.githubsearcher.ui.searchuser

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.*
import com.smparkworld.githubsearcher.R
import com.smparkworld.githubsearcher.data.repository.UserRepository
import com.smparkworld.githubsearcher.model.UsersUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class SearchUserViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _error = MutableLiveData<Int>()
    val error: LiveData<Int> = _error

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _isEmpty = MutableLiveData<Boolean>()
    val isEmpty: LiveData<Boolean> = _isEmpty

    private val _users = MutableLiveData<Flow<PagingData<UsersUiModel>>>()
    val users: LiveData<Flow<PagingData<UsersUiModel>>> = _users

    val searchId = MutableLiveData<String>()

    fun search() {
        val search = searchId.value
        if (search.isNullOrBlank()) {
            _error.value = R.string.activitySearchUser_searchEmpty
            return
        }

        _users.value = Pager(
            PagingConfig(pageSize = 50)
        ) {
            userRepository.searchUserById(search, 50)
        }.flow.map {
            it.map { item -> UsersUiModel.Item(item) }
                .insertSeparators { before, after ->
                    if (before is UsersUiModel.Item && after is UsersUiModel.Item) UsersUiModel.Separator else null
                }
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