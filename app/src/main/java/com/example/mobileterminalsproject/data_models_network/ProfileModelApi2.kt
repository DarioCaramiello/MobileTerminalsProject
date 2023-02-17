package com.example.mobileterminalsproject.data_models_network

import com.google.gson.annotations.SerializedName

data class ProfileModelApi2(
    @SerializedName("id")
    var id: String?,
    @SerializedName("abbreviation")
    var abbreviation: String?,
    @SerializedName("city")
    var city: String?,
    @SerializedName("conference")
    var conference: String?,
    @SerializedName("division")
    var division: String?,
    @SerializedName("full_name")
    var full_name: String?,
    @SerializedName("name")
    var name: String?
)