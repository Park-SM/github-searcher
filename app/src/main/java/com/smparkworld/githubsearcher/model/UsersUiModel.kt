package com.smparkworld.githubsearcher.model

import androidx.recyclerview.widget.DiffUtil

sealed class UsersUiModel {

    data class Item(val user: User) : UsersUiModel()
    object Separator : UsersUiModel()

    companion object {
        val DIFF_CALLBACK = object: DiffUtil.ItemCallback<UsersUiModel>() {

            override fun areItemsTheSame(oldItem: UsersUiModel, newItem: UsersUiModel) =
                if (oldItem is Item && newItem is Item) {
                    oldItem.user.name == newItem.user.name
                } else false

            override fun areContentsTheSame(oldItem: UsersUiModel, newItem: UsersUiModel) =
                if (oldItem is Item && newItem is Item) {
                    oldItem == newItem
                } else false
        }
    }
}