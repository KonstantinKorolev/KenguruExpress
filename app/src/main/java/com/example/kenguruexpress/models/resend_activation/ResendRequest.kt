package com.example.kenguruexpress.models.resend_activation

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ResendRequest {
    @SerializedName("email")
    @Expose
    var email: String? = null
}