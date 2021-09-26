package com.smparkworld.githubsearcher.ui.searchuser

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.smparkworld.githubsearcher.R
import com.smparkworld.githubsearcher.databinding.ItemSearchuserAdapterBinding
import com.smparkworld.githubsearcher.model.User
import com.smparkworld.githubsearcher.model.UsersUiModel

class UsersAdapter(
    private val onClickItem: (User) -> Unit
) : PagingDataAdapter<UsersUiModel, RecyclerView.ViewHolder>(UsersUiModel.DIFF_CALLBACK) {

    override fun getItemViewType(position: Int) =
            when(getItem(position)) {
                is UsersUiModel.Item -> R.layout.item_searchuser_adapter
                else              -> R.layout.item_searchuser_adapter_separator
            }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            when(viewType) {
                R.layout.item_searchuser_adapter -> {
                    UserViewHolder(
                        ItemSearchuserAdapterBinding.inflate(
                            LayoutInflater.from(parent.context), parent, false
                        ), onClickItem
                    )
                }
                else -> {
                    SeparatorViewHolder(
                        LayoutInflater.from(parent.context).inflate(
                            R.layout.item_searchuser_adapter_separator, parent, false
                        )
                    )
                }
            }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        if (item is UsersUiModel.Item && holder is UserViewHolder) {
            holder.bind(item.user)
        }
    }

    class UserViewHolder(
        private val binding: ItemSearchuserAdapterBinding,
        private val onClickItem: (User) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(user: User) {
            binding.onClick = onClickItem
            binding.user = user
            binding.executePendingBindings()
        }
    }

    class SeparatorViewHolder(view: View) : RecyclerView.ViewHolder(view)
}