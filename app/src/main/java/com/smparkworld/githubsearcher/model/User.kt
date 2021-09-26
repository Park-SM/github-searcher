package com.smparkworld.githubsearcher.model

import com.google.gson.annotations.SerializedName

data class User(

    @SerializedName("login")
    val uid: String,

    @SerializedName("name")
    val name: String?,

    @SerializedName("avatar_url")
    val avatarUrl: String,

    @SerializedName("bio")
    val bio: String?,

    var repos: List<Repo>?
) {
    fun getUidAndName() = if (name.isNullOrBlank()) uid else "$uid ($name)"
}