package com.example.kenguruexpress.api

import com.example.kenguruexpress.models.contacts.PostContactsRequest
import com.example.kenguruexpress.models.contacts.PostContactsResponse
import com.example.kenguruexpress.models.contacts.getContactsResponse
import retrofit2.Call
import retrofit2.http.*

interface AddressBookApi {
    @POST("profiles/{profile_id}/contacts/")
    fun postContacts(@Path("profile_id") profile_id: String,
                    @Body req: PostContactsRequest): Call<PostContactsResponse>

    @GET("profiles/{profile_id}/contacts/")
    fun getUserContacts(@Path("profile_id") profile_id: String,
                        @Query("page") page: Int,
                        @Query("limit") limit: Int,
                        ): Call<ArrayList<getContactsResponse>>
}