package com.smparkworld.githubsearcher.data.remote.api

import com.smparkworld.githubsearcher.model.Event
import com.smparkworld.githubsearcher.model.Repo
import com.smparkworld.githubsearcher.model.User
import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubAPI {

    @GET("/search/users")
    fun searchUsersById(
            @Query("q") id: String,
            @Query("per_page") size: Int,
            @Query("page") page: Int
    ): Single<SearchUsersResponse>

    @GET("/users/{uid}")
    fun getUserById(
            @Path("uid") uid: String
    ): Single<User>

    @GET("/users/{uid}/repos")
    suspend fun getReposById(
            @Path("uid") uid: String,
            @Query("sort") sort: String
    ): Response<List<Repo>>

    @GET("/users/{uid}/events")
    suspend fun getEventsById (
            @Path("uid") uid: String,
            @Query("per_page") size: Int,
            @Query("page") page: Int
    ): Response<List<Event>>
}