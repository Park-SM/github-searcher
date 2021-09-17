package com.smparkworld.githubsearcher.model

import androidx.recyclerview.widget.DiffUtil
import com.google.gson.annotations.SerializedName

data class Repo(

    @SerializedName("name")
    val name: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("language")
    val language: String,

    @SerializedName("stargazers_count")
    val numOfStar: String,
) {
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Repo>() {
            override fun areItemsTheSame(oldItem: Repo, newItem: Repo) =
                    oldItem.name == newItem.name
            override fun areContentsTheSame(oldItem: Repo, newItem: Repo) =
                    oldItem == newItem
        }
    }
}