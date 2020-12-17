package com.example.kenguruexpress.models.contacts

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class PostContactsResponse {
    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("locality")
    @Expose
    var locality: Int? = null

    @SerializedName("street")
    @Expose
    var street: String? = null

    @SerializedName("house")
    @Expose
    var house: String? = null

    @SerializedName("flat")
    @Expose
    var flat: String? = null

    @SerializedName("index_number")
    @Expose
    var index_number: String? = null

    @SerializedName("surname")
    @Expose
    var surname: String? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("patronymic")
    @Expose
    var patronymic: String? = null

    @SerializedName("company")
    @Expose
    var company: String? = null

    @SerializedName("phone")
    @Expose
    var phone: String? = null

    @SerializedName("phone_extension")
    @Expose
    var phone_extension: String? = null

    @SerializedName("comment")
    @Expose
    var comment: String? = null
}