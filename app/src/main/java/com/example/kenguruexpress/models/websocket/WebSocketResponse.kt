package com.example.kenguruexpress.models.websocket

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class WebSocketResponse {
    @field:Json(name="completed")
    var completed: Boolean? = null

    @field:Json(name="success")
    var success: Boolean? = null

    @field:Json(name="data")
    var data: Answer? = null

    @JsonClass(generateAdapter = true)
    class Answer {
        @field:Json(name="id")
        var id: Int? = null

        @field:Json(name="operator")
        var operator: String? = null

        @field:Json(name="rating")
        var rating: String? = null

        @field:Json(name="title")
        var title: String? = null

        @field:Json(name="common_price")
        var common_price: String? = null

        @field:Json(name="price")
        var price: String? = null

        @field:Json(name="term")
        var term: String? = null

        @field:Json(name="pickup")
        var pickup: Boolean? = null

        @field:Json(name="delivery")
        var delivery: Boolean? = null

        @field:Json(name="pickup_day")
        var pickup_day: String? = null

        @field:Json(name="delivery_day")
        var delivery_day: String? = null
    }
}