package com.smparkworld.githubsearcher.data.remote.api

import com.google.gson.annotations.SerializedName
import com.smparkworld.githubsearcher.model.User

data class SearchUsersResponse(

    @SerializedName("total_count")
    val totalCount: Int,

    @SerializedName("items")
    val items: List<User>
)

data class UserEventsResponse(

        @SerializedName("type")
        val type: String,

        @SerializedName("actor")
        val actor: User,

        @SerializedName("repo")
        val repo: EventTargetRepo
)

data class EventTargetRepo(

        @SerializedName("name")
        val name: String,

        @SerializedName("url")
        val url: String
)