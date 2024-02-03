package com.example.wanderlist.retrofit.user

import retrofit2.Response
import retrofit2.http.GET

interface UserServiceAPI {
    @GET("?results=25")
    suspend fun getData() : Response<RandomUser>

}