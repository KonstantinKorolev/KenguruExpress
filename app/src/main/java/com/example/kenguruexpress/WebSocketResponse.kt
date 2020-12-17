package com.example.kenguruexpress

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class WebSocketResponse {
    @SerializedName("success")
    @Expose
    var success: Boolean? = null

    @SerializedName("data")
    @Expose
    var data: Answer? = null

    class Answer {
        @SerializedName("id")
        @Expose
        var id: Int? = null

        @SerializedName("operator")
        @Expose
        var operator: String? = null

        @SerializedName("rating")
        @Expose
        var rating: String? = null

        @SerializedName("title")
        @Expose
        var title: String? = null

        @SerializedName("common_price")
        @Expose
        var common_price: String? = null

        @SerializedName("price")
        @Expose
        var price: String? = null

        @SerializedName("term")
        @Expose
        var term: String? = null

        @SerializedName("pickup")
        @Expose
        var pickup: String? = null

        @SerializedName("delivery")
        @Expose
        var delivery: String? = null

        @SerializedName("pickup_day")
        @Expose
        var pickup_day: String? = null

        @SerializedName("delivery_day")
        @Expose
        var delivery_day: String? = null
    }
}