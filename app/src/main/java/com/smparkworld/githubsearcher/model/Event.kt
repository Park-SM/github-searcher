package com.smparkworld.githubsearcher.model

import com.google.gson.annotations.SerializedName

data class Event(

        @SerializedName("id")
        val id: String,

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