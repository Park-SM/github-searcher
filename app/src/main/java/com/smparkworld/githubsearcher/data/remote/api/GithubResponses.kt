package com.smparkworld.githubsearcher.data.remote.api

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.smparkworld.githubsearcher.model.User

@Keep
data class SearchUsersResponse(

    @SerializedName("total_count")
    val totalCount: Int,

    @SerializedName("items")
    val items: List<User>
)