package com.example.kenguruexpress.models.users

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class RegistrationRequest {
    @SerializedName("email")
    @Expose
    var email: String? = null

    @SerializedName("password")
    @Expose
    var password: String? = null
}