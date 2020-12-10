package com.example.kenguruexpress.models.locality

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CityLocal {
    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("full_title")
    @Expose
    var full_title: String? = null

    @SerializedName("country")
    @Expose
    var country: String? = null

    @SerializedName("country_iso_code")
    @Expose
    var country_iso_code: String? = null

    @SerializedName("region")
    @Expose
    var region: String? = null

    @SerializedName("region_type")
    @Expose
    var region_type: String? = null

    @SerializedName("area")
    @Expose
    var area: String? = null

    @SerializedName("area_type")
    @Expose
    var area_type: String? = null

    @SerializedName("locality")
    @Expose
    var locality: String? = null

    @SerializedName("locality_type")
    @Expose
    var locality_type: String? = null

    @SerializedName("postcode")
    @Expose
    var postcode: String? = null

    @SerializedName("fias")
    @Expose
    var fias: String? = null

    @SerializedName("kladr")
    @Expose
    var kladr: String? = null

    @SerializedName("okato")
    @Expose
    var okato: String? = null

    @SerializedName("oktmo")
    @Expose
    var oktmo: String? = null

    @SerializedName("latitude")
    @Expose
    var latitude: String? = null

    @SerializedName("longitude")
    @Expose
    var longitude: String? = null
}