package com.smparkworld.githubsearcher.data.remote.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface UserAPI {

    @GET("/search/users")
    suspend fun searchById(
        @Query("q") id: String,
        @Query("per_page") size: Int,
        @Query("page") page: Int
    ): Response<SearchUsersResponse>
}