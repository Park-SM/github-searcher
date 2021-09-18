package com.smparkworld.githubsearcher.model

import androidx.recyclerview.widget.DiffUtil

sealed class DetailUserUIModel {

    data class Header(val user: User, val repos: List<Repo>): DetailUserUIModel()
    data class Item(val event: Event): DetailUserUIModel()
    object Separator: DetailUserUIModel()

    companion object {
        val DIFF_CALLBACK = object: DiffUtil.ItemCallback<DetailUserUIModel>() {

            override fun areItemsTheSame(oldItem: DetailUserUIModel, newItem: DetailUserUIModel) =
                if (oldItem is Item && newItem is Item) {
                    oldItem.event.id == newItem.event.id
                } else false

            override fun areContentsTheSame(oldItem: DetailUserUIModel, newItem: DetailUserUIModel) =
                if (oldItem is Item && newItem is Item) {
                    oldItem == newItem
                } else false
        }
    }
}