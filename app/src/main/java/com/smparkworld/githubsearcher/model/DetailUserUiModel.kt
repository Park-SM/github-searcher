package com.smparkworld.githubsearcher.model

import androidx.recyclerview.widget.DiffUtil

sealed class DetailUserUiModel {

    data class Header(val user: User): DetailUserUiModel()
    data class Item(val event: Event): DetailUserUiModel()
    object Separator: DetailUserUiModel()

    companion object {
        val DIFF_CALLBACK = object: DiffUtil.ItemCallback<DetailUserUiModel>() {

            override fun areItemsTheSame(oldItem: DetailUserUiModel, newItem: DetailUserUiModel) =
                if (oldItem is Item && newItem is Item) {
                    oldItem.event.id == newItem.event.id
                } else false

            override fun areContentsTheSame(oldItem: DetailUserUiModel, newItem: DetailUserUiModel) =
                if (oldItem is Item && newItem is Item) {
                    oldItem == newItem
                } else false
        }
    }
}