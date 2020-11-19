package com.example.kenguruexpress.models.users

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ReqistrationResponse {
        @SerializedName("email")
        @Expose
        var email: String? = null

        @SerializedName("id")
        @Expose
        var id: Int? = null

        @SerializedName("password")
        @Expose
        var password: String? = null
}
