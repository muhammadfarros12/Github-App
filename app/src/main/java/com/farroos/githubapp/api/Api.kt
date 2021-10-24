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

//    @Headers("Authorization: token github")
//    dihapus dikarenakan udah diwakili di Object RetrofitClient
    @GET("search/users")
    fun getSearchUsers(
        @Query("q") query: String
    ): Call<UserResponse>

    @GET("users/{username}")

    fun getUserDetail(
        @Path("username") username: String
    ): Call<DetailUserResponse>

    @GET("users/{username}/followers")

    fun getFollowers(
        @Path("username") username: String
    ): Call<ArrayList<User>>

    @GET("users/{username}/following")

    fun getFollowing(
        @Path("username") username: String
    ): Call<ArrayList<User>>
}