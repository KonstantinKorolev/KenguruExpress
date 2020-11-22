package com.example.kenguruexpress.api


import com.example.kenguruexpress.models.email_activation.emailActivationRequest
import com.example.kenguruexpress.models.login.LoginRequest
import com.example.kenguruexpress.models.login.LoginResponse
import com.example.kenguruexpress.models.resend_email.ResendRequest
import com.example.kenguruexpress.models.users.RegistrationRequest
import com.example.kenguruexpress.models.users.ReqistrationResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApi {
    @POST("users/")
    fun createUser(@Body req: RegistrationRequest): Call<ReqistrationResponse>

    @POST("users/resend_activation/")
    fun resendEmail(@Body req: ResendRequest): Call<ResponseBody>

    @POST("users/login/")
    fun login(@Body login: LoginRequest): Call<LoginResponse>

    @POST("/users/activation/")
    fun activationEmail(@Body req: emailActivationRequest): Call<ResponseBody>
}