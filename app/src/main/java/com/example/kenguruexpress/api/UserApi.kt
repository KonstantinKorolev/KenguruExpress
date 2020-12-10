package com.example.kenguruexpress.api


import com.example.kenguruexpress.models.email_activation.emailActivationRequest
import com.example.kenguruexpress.models.login.LoginRequest
import com.example.kenguruexpress.models.login.LoginResponse
import com.example.kenguruexpress.models.phone_activation.PhoneActivationRequest
import com.example.kenguruexpress.models.phone_reqistration.PhoneReqistrationRequest
import com.example.kenguruexpress.models.resend_email.ResendRequest
import com.example.kenguruexpress.models.users.RegistrationRequest
import com.example.kenguruexpress.models.users.ReqistrationResponse
import com.example.kenguruexpress.models.usersMe.changeUserInfResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface UserApi {
    @POST("users/")
    fun createUser(@Body req: RegistrationRequest): Call<ReqistrationResponse>

    @POST("users/resend_activation/")
    fun resendEmail(@Body req: ResendRequest): Call<ResponseBody>

    @POST("users/login/")
    fun login(@Body login: LoginRequest): Call<LoginResponse>

    @POST("users/activation/")
    fun activationEmail(@Body req: emailActivationRequest): Call<ResponseBody>

    @POST("users/register_phone/")
    fun reqistrationPhone(@Body req: PhoneReqistrationRequest): Call<ResponseBody>

    @POST("users/verify_phone/")
    fun activationPhone(@Body req: PhoneActivationRequest): Call<ResponseBody>

    @FormUrlEncoded
    @PATCH("users/me")
    fun changeInfo(
            @Field("first_name") first_name: String,
            @Field("last_name") last_name: String,
            @Field("patronymic") patronymic: String
    ): Call<changeUserInfResponse>
}