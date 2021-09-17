package com.smparkworld.githubsearcher.data.remote.api

import com.google.gson.annotations.SerializedName
import com.smparkworld.githubsearcher.model.User

data class SearchUsersResponse(

    @SerializedName("total_count")
    val totalCount: Int,

    @SerializedName("items")
    val items: List<User>
)