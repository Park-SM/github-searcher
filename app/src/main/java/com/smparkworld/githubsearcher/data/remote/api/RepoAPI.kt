package com.smparkworld.githubsearcher.data.remote.api

import com.smparkworld.githubsearcher.model.Repo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RepoAPI {

    @GET("/users/{uid}/repos")
    suspend fun getById(
            @Path("uid") uid: String
    ): Response<List<Repo>>
}