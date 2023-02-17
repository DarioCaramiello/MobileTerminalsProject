package com.example.mobileterminalsproject.data_models_network

import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.annotations.SerializedName

data class ProfileModelYoutubeDownloadApi (
    @JsonProperty("title")
    var title: String?,
    @JsonProperty("thumb")
    var thumb: String?,
    @JsonProperty("length")
    var length: String?,
    @JsonProperty("lengthsec")
    var lengthsec: String?,
    @JsonProperty("keywords")
    var keywords: String?,
    @JsonProperty("avg_rating")
    var avg_rating: String?,
    @JsonProperty("author")
    var author: String?,
    @JsonProperty("channelid")
    var channelid: String?,
    @JsonProperty("username")
    var username: String?,
    @JsonProperty("viewcount")
    var viewcount: String?,
    @JsonProperty("date")
    var date: String?,
    @JsonProperty("description")
    var description: String?,
    @JsonProperty("category")
    var category: String?,
    @JsonProperty("link")
    var link: List<ProfileModelYoutubeLinkApi>,
    @JsonProperty("msg")
    var msg: String?,
    @JsonProperty("status")
    var status: String?,
    @JsonProperty("cleantitle")
    var cleantitle: String?,
    @JsonProperty("related")
    var related: String?,
    @JsonProperty("altserver")
    var altserver: String?,
    @JsonProperty("lexp")
    var lexp: String?,
    @JsonProperty("age")
    var age: String?
        )

data class ProfileModelYoutubeLinkApi(
    @SerializedName("17")
    @JsonProperty("17")
    var link17: String?,
    @SerializedName("18")
    @JsonProperty("18")
    var link18: String?,
    @SerializedName("22")
    @JsonProperty("22")
    var link22: String?,
    @SerializedName("133")
    @JsonProperty("133")
    var link133: String?,
    @SerializedName("134")
    @JsonProperty("134")
    var link134: String?,
    @SerializedName("135")
    @JsonProperty("135")
    var link135: String?,
    @SerializedName("136")
    @JsonProperty("136")
    var link136: String?,
    @SerializedName("139")
    @JsonProperty("139")
    var link139: String?,
    @SerializedName("140")
    @JsonProperty("140")
    var link140: String?,
    @SerializedName("160")
    @JsonProperty("160")
    var link160: String?,
    @SerializedName("242")
    @JsonProperty("242")
    var link242: String?,
    @SerializedName("243")
    @JsonProperty("243")
    var link243: String?,
    @SerializedName("244")
    @JsonProperty("244")
    var link244: String?,
    @SerializedName("247")
    @JsonProperty("247")
    var link247: String?,
    @SerializedName("249")
    @JsonProperty("249")
    var link249: String?,
    @SerializedName("250")
    @JsonProperty("250")
    var link250: String?,
    @SerializedName("251")
    @JsonProperty("251")
    var link251: String?,
    @SerializedName("278")
    @JsonProperty("278")
    var link278: String?,
    @SerializedName("394")
    @JsonProperty("394")
    var link394: String?,
    @SerializedName("395")
    @JsonProperty("395")
    var link395: String?,
    @SerializedName("396")
    @JsonProperty("396")
    var link396: String?,
    @SerializedName("397")
    @JsonProperty("397")
    var link397: String?,
    @SerializedName("398")
    @JsonProperty("398")
    var link398: String?
)