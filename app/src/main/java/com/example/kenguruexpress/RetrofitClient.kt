package com.example.kenguruexpress

import android.content.Context
import android.text.TextWatcher
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {
    fun getRetrofitClient(context: Context): Retrofit {
        val gson = GsonBuilder().setLenient().create()
        return Retrofit.Builder()
            .baseUrl("http://68.183.30.45/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okhttpClient(context))
            .build()
    }

    private fun okhttpClient(context: Context): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(AuthInterceptor(context))
                .build()
    }
}