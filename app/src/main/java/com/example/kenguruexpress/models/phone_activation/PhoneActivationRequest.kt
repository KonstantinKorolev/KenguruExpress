package com.example.kenguruexpress.models.phone_activation

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class PhoneActivationRequest {
    @SerializedName("phone")
    @Expose
    var phone: String? = null

    @SerializedName("code")
    @Expose
    var code: String? = null
}