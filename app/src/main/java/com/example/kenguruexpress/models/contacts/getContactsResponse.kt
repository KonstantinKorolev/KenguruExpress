package com.example.kenguruexpress.models.contacts

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class getContactsResponse {
    @SerializedName("count")
    @Expose
    var count: Int? = null

    @SerializedName("next")
    @Expose
    var next: String? = null

    @SerializedName("previous")
    @Expose
    var previous: String? = null

    @SerializedName("results")
    @Expose
    var results: Contact? = null

    class Contact {
        @SerializedName("id")
        @Expose
        var id: Int? = null

        @SerializedName("user")
        @Expose
        var user: Int? = null

        @SerializedName("locality")
        @Expose
        var locality: Locality? = null

        class Locality {
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

        @SerializedName("street")
        @Expose
        var street: String? = null

        @SerializedName("street_type")
        @Expose
        var street_type: String? = null

        @SerializedName("house")
        @Expose
        var house: String? = null

        @SerializedName("house_type")
        @Expose
        var house_type: String? = null

        @SerializedName("block")
        @Expose
        var block: String? = null

        @SerializedName("block_type")
        @Expose
        var block_type: String? = null

        @SerializedName("flat")
        @Expose
        var flat: String? = null

        @SerializedName("flat_type")
        @Expose
        var flat_type: String? = null

        @SerializedName("house_fias")
        @Expose
        var house_fias: String? = null

        @SerializedName("street_fias")
        @Expose
        var street_fias: String? = null

        @SerializedName("index_number")
        @Expose
        var index_number: String? = null

        @SerializedName("company")
        @Expose
        var company: String? = null

        @SerializedName("surname")
        @Expose
        var surname: String? = null

        @SerializedName("name")
        @Expose
        var name: String? = null

        @SerializedName("patronymic")
        @Expose
        var patronymic: String? = null

        @SerializedName("phone")
        @Expose
        var phone: String? = null

        @SerializedName("phone_extension")
        @Expose
        var phone_extension: String? = null

        @SerializedName("phone2")
        @Expose
        var phone2: String? = null

        @SerializedName("phone2_extension")
        @Expose
        var phone2_extension: String? = null

        @SerializedName("comment")
        @Expose
        var comment: String? = null

        @SerializedName("latitude")
        @Expose
        var latitude: String? = null

        @SerializedName("longitude")
        @Expose
        var longitude: String? = null
    }

}