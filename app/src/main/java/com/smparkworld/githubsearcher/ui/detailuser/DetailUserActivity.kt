package com.smparkworld.githubsearcher.ui.detailuser

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.smparkworld.githubsearcher.GithubSearcherApp
import com.smparkworld.githubsearcher.R
import com.smparkworld.githubsearcher.databinding.ActivityDetailuserBinding
import com.smparkworld.githubsearcher.extension.showSnackbar
import com.smparkworld.githubsearcher.ui.detailuser.di.DetailUserComponent
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.lang.RuntimeException
import javax.inject.Inject

class DetailUserActivity : AppCompatActivity() {

    private lateinit var detailUserComponent: DetailUserComponent

    lateinit var binding: ActivityDetailuserBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<DetailUserViewModel> { viewModelFactory }

    private val uid by lazy {
        intent.getStringExtra("uid") ?: throw RuntimeException("uid is null.")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        detailUserComponent = (application as GithubSearcherApp).appComponent.detailUserComponent().create()
        detailUserComponent.inject(this)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityDetailuserBinding>(
                this, R.layout.activity_detailuser
        ).apply {
            setSupportActionBar(toolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_new_24)

            lifecycleOwner = this@DetailUserActivity
            vm = viewModel
        }
        viewModel.loadUser(uid)
        initObservers()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initObservers() {
        viewModel.error.observe(this) { showSnackbar(it) }
        viewModel.events.observe(this) { flow ->

            val adapter = DetailUserAdapter().apply {
                addLoadStateListener { state ->
                    viewModel.setEventEmpty(
                        // User 정보를 보여주는 Header 가 있기 때문에 itemCount == 1로 Event 가 비어있는지 확인
                        if (state.refresh is LoadState.NotLoading && itemCount == 1) null else state
                    )
                }
                binding.rvContainer.adapter = withLoadStateFooter(EventLoadStateAdapter(::retry))
            }
            lifecycleScope.launch {
                flow.collectLatest { adapter.submitData(it) }
            }
        }
    }
}