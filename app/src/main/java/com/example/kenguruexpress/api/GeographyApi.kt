package com.example.kenguruexpress.api

import com.example.kenguruexpress.models.locality.CityLocal
import com.example.kenguruexpress.models.locality.HouseLocal
import com.example.kenguruexpress.models.locality.StreetLocal
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GeographyApi {
    @GET("geo/locality/")
    fun getCityLocality(@Query("term") term: String,
                    @Query("count") count: Int): Call<ArrayList<CityLocal>>

    @GET("geo/address/street/")
    fun getStreetLocality(@Query("kladr_id") kladr_id: String,
                          @Query("term") term: String,
                          @Query("count") count: Int): Call<ArrayList<StreetLocal>>

    @GET("geo/address/house/")
    fun getHouseLocality(@Query("street_fias") street_fias: String,
                         @Query("term") term: String,
                         @Query("count") count: Int): Call<ArrayList<HouseLocal>>
}