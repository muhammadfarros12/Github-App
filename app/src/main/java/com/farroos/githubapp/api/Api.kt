package com.farroos.githubapp.api

import com.farroos.githubapp.data.model.DetailUserResponse
import com.farroos.githubapp.data.model.User
import com.farroos.githubapp.data.model.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getSearchUsers(
        @Query("q") query: String
    ): Call<UserResponse>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getUserDetail(
        @Path("username") username: String
    ): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getFollowers(
        @Path("username") username: String
    ): Call<ArrayList<User>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getFollowing(
        @Path("username") username: String
    ): Call<ArrayList<User>>
}