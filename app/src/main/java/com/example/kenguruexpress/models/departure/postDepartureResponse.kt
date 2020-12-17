package com.example.kenguruexpress.models.departure

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class postDepartureResponse {
    @SerializedName("pickup")
    @Expose
    var pickup: Boolean? = null

    @SerializedName("delivery")
    @Expose
    var delivery: Boolean? = null

    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("sender_city")
    @Expose
    var sender_city: Int? = null

    @SerializedName("receiver_city")
    @Expose
    var receiver_city: Int? = null

    @SerializedName("cargoes")
    @Expose
    var cargoes: List<Int>? = null
}