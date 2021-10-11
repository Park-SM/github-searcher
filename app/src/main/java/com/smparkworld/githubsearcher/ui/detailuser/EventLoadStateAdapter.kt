package com.smparkworld.githubsearcher.ui.detailuser

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.smparkworld.githubsearcher.R
import com.smparkworld.githubsearcher.databinding.ItemSearchuserLoadstateAdatperBinding

class EventLoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<EventLoadStateAdapter.StateViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState) = StateViewHolder(
        ItemSearchuserLoadstateAdatperBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ), retry
    )

    override fun onBindViewHolder(holder: StateViewHolder, loadState: LoadState) = holder.bind(loadState)

    class StateViewHolder(
        private val binding: ItemSearchuserLoadstateAdatperBinding,
        private val retry: () -> Unit
    ): RecyclerView.ViewHolder(binding.root) {

        init {
            binding.btnRetry.setOnClickListener { retry() }
        }

        fun bind(loadState: LoadState) {
            binding.progressbar.isVisible = loadState is LoadState.Loading
            binding.errorLayout.isVisible = loadState is LoadState.Error

            if (loadState is LoadState.Error) {
                when {
                    loadState.error.message?.contains("422") == true -> {
                        binding.root.isVisible = false
                    }
                    loadState.error.message?.contains("403") == true -> {
                        binding.tvErrorMsg.setText(R.string.error_fatalNetwork)
                    }
                    else -> {
                        binding.tvErrorMsg.setText(R.string.error_failedToConnectNetwork)
                    }
                }
            }
        }
    }

}