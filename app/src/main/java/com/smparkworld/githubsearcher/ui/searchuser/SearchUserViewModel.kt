package com.smparkworld.githubsearcher.ui.searchuser

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.smparkworld.githubsearcher.R
import com.smparkworld.githubsearcher.data.repository.UserRepository
import com.smparkworld.githubsearcher.model.Result
import com.smparkworld.githubsearcher.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchUserViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _error = MutableLiveData<Int>()
    val error: LiveData<Int> = _error

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _users = MutableLiveData<Flow<PagingData<User>>>()
    val users: LiveData<Flow<PagingData<User>>> = _users

    val searchId = MutableLiveData<String>()

    fun search() {

        if (searchId.value.isNullOrBlank()) {
            _error.value = R.string.activitySearchUser_searchEmpty
            return
        }
        val search = searchId.value!!

        viewModelScope.launch {
            _loading.value = true

            val result = userRepository.searchUserById(search)
            if (result is Result.Success) {
                _users.value = result.data
            } else {
                (result as Result.Error).printStackTrace()
                _error.value = R.string.activitySearchUser_searchFail
            }
            _loading.value = false
        }
    }
}