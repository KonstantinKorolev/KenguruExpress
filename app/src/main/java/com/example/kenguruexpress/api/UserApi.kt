package com.example.kenguruexpress.api


import com.example.kenguruexpress.models.resend_activation.ResendRequest
import com.example.kenguruexpress.models.users.RegistrationRequest
import com.example.kenguruexpress.models.users.ReqistrationResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApi {
    @POST("users/")
    fun createUser(
        @Body req: RegistrationRequest
    ): Call<ReqistrationResponse>

    @POST("users/resend_activation/")
    fun resendEmail(@Body req: ResendRequest): Call<ResponseBody>
}