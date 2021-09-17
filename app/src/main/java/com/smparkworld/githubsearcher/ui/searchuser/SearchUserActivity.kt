package com.smparkworld.githubsearcher.ui.searchuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.smparkworld.githubsearcher.GithubSearcherApp
import com.smparkworld.githubsearcher.R
import com.smparkworld.githubsearcher.data.repository.PagingLoadStateAdapter
import com.smparkworld.githubsearcher.databinding.ActivitySearchuserBinding
import com.smparkworld.githubsearcher.extension.showSnackbar
import com.smparkworld.githubsearcher.model.User
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchUserActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var binding: ActivitySearchuserBinding

    private val viewModel by viewModels<SearchUserViewModel> { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as GithubSearcherApp).appComponent.searchUserComponent().create().inject(this)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivitySearchuserBinding>(
            this, R.layout.activity_searchuser
        ).apply {
            lifecycleOwner = this@SearchUserActivity
            vm = viewModel
        }
        initObservers()
    }

    private fun onClickItem(user: User) {
        Toast.makeText(this, "Selected name is ${user.name}!", Toast.LENGTH_SHORT).show()
    }

    private fun initObservers() {
        viewModel.error.observe(this) { showSnackbar(it) }
        viewModel.users.observe(this) { flow ->

            lifecycleScope.launch {
                val adapter = UsersAdapter(::onClickItem).apply {
                    addLoadStateListener { state ->
                        if (state.refresh is LoadState.NotLoading && itemCount == 0) {
                            viewModel.setUsersLoadState(null)
                        } else {
                            viewModel.setUsersLoadState(state)
                        }
                    }
                }
                binding.rvUsers.adapter = adapter.withLoadStateFooter(
                    PagingLoadStateAdapter(adapter::retry)
                )
                flow.collectLatest {
                    adapter.submitData(it)
                }
            }
        }
    }
}