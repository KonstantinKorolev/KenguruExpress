package com.example.kenguruexpress.models.login

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class LoginRequest {
    @SerializedName("password")
    @Expose
    var password: String? = null

    @SerializedName("email")
    @Expose
    var email: String? = null
}