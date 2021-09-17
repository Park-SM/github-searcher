package com.smparkworld.githubsearcher.ui.detailuser

import androidx.lifecycle.*
import com.smparkworld.githubsearcher.R
import com.smparkworld.githubsearcher.data.repository.RepoRepository
import com.smparkworld.githubsearcher.data.repository.UserRepository
import com.smparkworld.githubsearcher.model.Repo
import com.smparkworld.githubsearcher.model.Result
import com.smparkworld.githubsearcher.model.User
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailUserViewModel @Inject constructor(
        private val userRepository: UserRepository,
        private val repoRepository: RepoRepository
) : ViewModel() {

    private val _error = MutableLiveData<Int>()
    val error: LiveData<Int> = _error

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    private val _repos = MutableLiveData<List<Repo>>()
    val repos: LiveData<List<Repo>> = _repos

    val reposEmpty: LiveData<Boolean> = Transformations.map(_repos) { it.isEmpty() }

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
                val user  = results[0] as Result<User>
                val repos = results[1] as Result<List<Repo>>

                if (user is Result.Success && repos is Result.Success) {
                    _user.value  = user.data
                    _repos.value = repos.data
                } else {
                    _error.value = R.string.error_failedToConnectNetwork
                    (user  as? Result.Error)?.printStackTrace()
                    (repos as? Result.Error)?.printStackTrace()
                }
                _loading.value = false
            }
        }
    }

    fun refresh() {
        uid?.let { loadUser(it) }
    }

}