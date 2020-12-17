package com.example.kenguruexpress.api

import com.example.kenguruexpress.models.departure.postDepartureRequest
import com.example.kenguruexpress.models.departure.postDepartureResponse
import com.example.kenguruexpress.models.products.CreateProductRequest
import com.example.kenguruexpress.models.products.CreateProductResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface ProductApi {
    @POST("products/")
    fun postProduct(@Body req: CreateProductRequest): Call<CreateProductResponse>

    @POST("departures/")
    fun postDeparture(@Body req: postDepartureRequest): Call<postDepartureResponse>
}