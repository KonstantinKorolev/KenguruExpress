package com.example.kenguruexpress.models.usersMe

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class changeUserInfResponse {
    @SerializedName("first_name")
    @Expose
    var first_name: String? = null

    @SerializedName("last_name")
    @Expose
    var last_name: String? = null

    @SerializedName("patronymic")
    @Expose
    var patronymic: String? = null
}