package com.smparkworld.githubsearcher.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
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

@Keep
data class EventTargetRepo(

        @SerializedName("name")
        val name: String,

        @SerializedName("url")
        val url: String
)