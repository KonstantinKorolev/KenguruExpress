package com.example.kenguruexpress.models.email_activation

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class emailActivationRequest {
    @SerializedName("uid")
    @Expose
    var uid: String? = null

    @SerializedName("token")
    @Expose
    var token: String? = null
}