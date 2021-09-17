package com.smparkworld.githubsearcher.data.remote.api

import com.smparkworld.githubsearcher.model.Repo
import com.smparkworld.githubsearcher.model.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubAPI {

    @GET("/search/users")
    suspend fun searchUsersById(
        @Query("q") id: String,
        @Query("per_page") size: Int,
        @Query("page") page: Int
    ): Response<SearchUsersResponse>

    @GET("/users/{uid}")
    suspend fun getUserById(
            @Path("uid") uid: String
    ): Response<User>

    @GET("/users/{uid}/repos")
    suspend fun getReposById(
            @Path("uid") uid: String
    ): Response<List<Repo>>
}