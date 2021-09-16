package com.smparkworld.githubsearcher.ui.searchuser

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.smparkworld.githubsearcher.databinding.ItemSearchuserAdapterBinding
import com.smparkworld.githubsearcher.model.User

class UsersAdapter(
    private val onClickItem: (User) -> Unit
) : PagingDataAdapter<User, UsersAdapter.UserViewHolder>(User.DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = UserViewHolder(
        ItemSearchuserAdapterBinding.inflate(
            LayoutInflater.from(parent.context), parent, false  // 이부분 parent, false 제거해보기
        ), onClickItem
    )

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item)
        }
    }

    class UserViewHolder(
        private val binding: ItemSearchuserAdapterBinding,
        private val onClickItem: (User) -> Unit
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(user: User) {
            binding.onClick = onClickItem
            binding.user = user
            binding.executePendingBindings()
        }
    }
}