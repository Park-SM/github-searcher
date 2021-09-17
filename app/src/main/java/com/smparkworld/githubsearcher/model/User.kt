package com.smparkworld.githubsearcher.model

import androidx.recyclerview.widget.DiffUtil
import com.google.gson.annotations.SerializedName

data class User(

    @SerializedName("login")
    val uid: String,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("avatar_url")
    val avatarUrl: String,

    @SerializedName("bio")
    val bio: String? = null
)

sealed class UserModel {

    data class Item(val user: User) : UserModel()
    object Separator : UserModel()

    companion object {
        val DIFF_CALLBACK = object: DiffUtil.ItemCallback<UserModel>() {

            override fun areItemsTheSame(oldItem: UserModel, newItem: UserModel) =
                    if (oldItem is Item && newItem is Item) {
                        oldItem.user.name == newItem.user.name
                    } else false

            override fun areContentsTheSame(oldItem: UserModel, newItem: UserModel) =
                    if (oldItem is Item && newItem is Item) {
                        oldItem == newItem
                    } else false
        }
    }
}