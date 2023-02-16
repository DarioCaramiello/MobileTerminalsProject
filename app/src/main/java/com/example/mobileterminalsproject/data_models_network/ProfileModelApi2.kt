package com.example.mobileterminalsproject.data_models_network

import com.fasterxml.jackson.annotation.JsonProperty

data class ProfileModelApi2(
    @JsonProperty("id")
    var id: String?,
    @JsonProperty("abbreviation")
    var abbreviation: String?,
    @JsonProperty("city")
    var city: String?,
    @JsonProperty("conference")
    var conference: String?,
    @JsonProperty("division")
    var division: String?,
    @JsonProperty("full_name")
    var full_name: String?,
    @JsonProperty("name")
    var name: String?
)