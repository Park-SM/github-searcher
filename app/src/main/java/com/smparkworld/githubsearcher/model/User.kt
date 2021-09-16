package com.smparkworld.githubsearcher.model

import androidx.recyclerview.widget.DiffUtil
import com.google.gson.annotations.SerializedName

data class User(

    @SerializedName("login")
    val name: String,

    @SerializedName("avatar_url")
    val avatarUrl: String,

    @SerializedName("bio")
    val bio: String? = null
) {
    companion object {
        val DIFF_CALLBACK = object: DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User) =
                oldItem.name == newItem.name
            override fun areContentsTheSame(oldItem: User, newItem: User) =
                oldItem == newItem
        }
    }
}