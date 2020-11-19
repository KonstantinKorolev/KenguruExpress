package com.example.kenguruexpress

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {
    fun getRetrofitClient(): Retrofit {
        val gson = GsonBuilder().setLenient().create()
        return Retrofit.Builder()
            .baseUrl("http://68.183.30.45/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }
}