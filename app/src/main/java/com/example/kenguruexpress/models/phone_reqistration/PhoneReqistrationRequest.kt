package com.example.kenguruexpress.models.phone_reqistration

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class PhoneReqistrationRequest {
    @SerializedName("phone")
    @Expose
    var phone: String? = null
}