package com.example.wanderlist.retrofit.user

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UserAPIInstance {
    companion object{
        var baseUrl = "https://randomuser.me/api/"

        fun getRetrofitInstance() : Retrofit{
            return Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create((GsonBuilder()).create())).build()
        }
    }
}