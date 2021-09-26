package com.smparkworld.githubsearcher.ui.detailuser

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.smparkworld.githubsearcher.R
import com.smparkworld.githubsearcher.databinding.ItemDetailuserAdapterEventBinding
import com.smparkworld.githubsearcher.databinding.ItemDetailuserAdapterUserinfoBinding
import com.smparkworld.githubsearcher.model.Event
import com.smparkworld.githubsearcher.model.DetailUserUiModel

class DetailUserAdapter
    : PagingDataAdapter<DetailUserUiModel, RecyclerView.ViewHolder>(DetailUserUiModel.DIFF_CALLBACK) {

    override fun getItemViewType(position: Int) =
            when(getItem(position)) {
                is DetailUserUiModel.Header -> R.layout.item_detailuser_adapter_userinfo
                is DetailUserUiModel.Item   -> R.layout.item_detailuser_adapter_event
                else                        -> R.layout.item_detailuser_adapter_separator
            }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            when(viewType) {
                R.layout.item_detailuser_adapter_userinfo -> {
                    UserInfoViewHolder(
                        ItemDetailuserAdapterUserinfoBinding.inflate(
                            LayoutInflater.from(parent.context), parent, false
                        )
                    )
                }
                R.layout.item_detailuser_adapter_event -> {
                    EventViewHolder(
                        ItemDetailuserAdapterEventBinding.inflate(
                            LayoutInflater.from(parent.context), parent, false
                        )
                    )
                }
                else -> {
                    SeparatorViewHolder(
                        LayoutInflater.from(parent.context).inflate(
                            R.layout.item_detailuser_adapter_separator, parent, false
                        )
                    )
                }
            }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is DetailUserUiModel.Header -> if (holder is UserInfoViewHolder) {
                holder.bind(item)
            }
            is DetailUserUiModel.Item   -> if (holder is EventViewHolder) {
                holder.bind(item.event)
            }
        }
    }


    class UserInfoViewHolder(
            private val binding: ItemDetailuserAdapterUserinfoBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(header: DetailUserUiModel.Header) {
            binding.user  = header.user
            binding.rvRepos.adapter = RepoAdapter()
            binding.executePendingBindings()
        }
    }

    class EventViewHolder(
            private val binding: ItemDetailuserAdapterEventBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(event: Event) {
            binding.event = event
            binding.executePendingBindings()
        }
    }

    class SeparatorViewHolder(view: View) : RecyclerView.ViewHolder(view)
}