package com.example.kenguruexpress.models.products

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CreateProductRequest {
    @SerializedName("delivery_type")
    @Expose
    var delivery_type: String? = null

    @SerializedName("height")
    @Expose
    var height: String? = null

    @SerializedName("length")
    @Expose
    var length: String? = null

    @SerializedName("width")
    @Expose
    var width: String? = null

    @SerializedName("weight")
    @Expose
    var weight: String? = null
}