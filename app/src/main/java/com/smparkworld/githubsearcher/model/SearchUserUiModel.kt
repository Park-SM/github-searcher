package com.smparkworld.githubsearcher.model

import androidx.recyclerview.widget.DiffUtil

sealed class SearchUserUiModel {

    data class Item(val user: User) : SearchUserUiModel()
    object Separator : SearchUserUiModel()

    companion object {
        val DIFF_CALLBACK = object: DiffUtil.ItemCallback<SearchUserUiModel>() {

            override fun areItemsTheSame(oldItem: SearchUserUiModel, newItem: SearchUserUiModel) =
                if (oldItem is Item && newItem is Item) {
                    oldItem.user.name == newItem.user.name
                } else false

            override fun areContentsTheSame(oldItem: SearchUserUiModel, newItem: SearchUserUiModel) =
                if (oldItem is Item && newItem is Item) {
                    oldItem == newItem
                } else false
        }
    }
}