package com.example.kenguruexpress.models.locality

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class HouseLocal {
    @SerializedName("house_type")
    @Expose
    var house_type: String? = null

    @SerializedName("house_type_full")
    @Expose
    var house_type_full: String? = null

    @SerializedName("house")
    @Expose
    var house: String? = null

    @SerializedName("house_fias_id")
    @Expose
    var house_fias_id: String? = null

    @SerializedName("house_kladr_id")
    @Expose
    var house_kladr_id: String? = null

    @SerializedName("block_type")
    @Expose
    var block_type: String? = null

    @SerializedName("block_type_full")
    @Expose
    var block_type_full: String? = null

    @SerializedName("block")
    @Expose
    var block: String? = null

    @SerializedName("postal_code")
    @Expose
    var postal_code: String? = null

    @SerializedName("latitude")
    @Expose
    var latitude: String? = null

    @SerializedName("longitude")
    @Expose
    var longitude: String? = null

    @SerializedName("unrestricted_value")
    @Expose
    var unrestricted_value: String? = null
}