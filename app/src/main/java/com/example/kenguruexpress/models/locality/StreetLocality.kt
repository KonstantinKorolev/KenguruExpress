package com.example.kenguruexpress.models.locality

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class StreetLocal {
    @SerializedName("street_with_type")
    @Expose
    var street_with_type: String? = null

    @SerializedName("street_type")
    @Expose
    var street_type: String? = null

    @SerializedName("street_type_full")
    @Expose
    var street_type_full: String? = null

    @SerializedName("street")
    @Expose
    var street: String? = null

    @SerializedName("street_fias_id")
    @Expose
    var street_fias_id: String? = null

    @SerializedName("street_kladr_id")
    @Expose
    var street_kladr_id: String? = null

    @SerializedName("postal_code")
    @Expose
    var postal_code: String? = null

    @SerializedName("unrestricted_value")
    @Expose
    var unrestricted_value: String? = null

    @SerializedName("city_fias_id")
    @Expose
    var city_fias_id: String? = null

    @SerializedName("settlement_fias_id")
    @Expose
    var settlement_fias_id: String? = null

}