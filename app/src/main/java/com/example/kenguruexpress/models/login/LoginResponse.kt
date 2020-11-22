package com.example.kenguruexpress.models.login

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class LoginResponse {
    @SerializedName("token")
    @Expose
    var auth_token: String? = null
}