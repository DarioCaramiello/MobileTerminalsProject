package com.example.mobileterminalsproject.data_models_network

import com.google.gson.annotations.SerializedName

data class ProfileModelYoutubeDownloadApi (
    @SerializedName("title")
    var title: String?,
    @SerializedName("thumb")
    var thumb: String?,
    @SerializedName("length")
    var length: String?,
    @SerializedName("lengthsec")
    var lengthsec: String?,
    @SerializedName("keywords")
    var keywords: String?,
    @SerializedName("avg_rating")
    var avg_rating: String?,
    @SerializedName("author")
    var author: String?,
    @SerializedName("channelid")
    var channelid: String?,
    @SerializedName("username")
    var username: String?,
    @SerializedName("viewcount")
    var viewcount: String?,
    @SerializedName("date")
    var date: String?,
    @SerializedName("description")
    var description: String?,
    @SerializedName("category")
    var category: String?,
    @SerializedName("link")
    var link: List<ProfileModelYoutubeLinkApi>,
    @SerializedName("msg")
    var msg: String?,
    @SerializedName("status")
    var status: String?,
    @SerializedName("cleantitle")
    var cleantitle: String?,
    @SerializedName("related")
    var related: String?,
    @SerializedName("altserver")
    var altserver: String?,
    @SerializedName("lexp")
    var lexp: String?,
    @SerializedName("age")
    var age: String?
        )

data class ProfileModelYoutubeLinkApi(
    @SerializedName("17")
    var link17: String?,
    @SerializedName("18")
    var link18: String?,
    @SerializedName("22")
    var link22: String?,
    @SerializedName("133")
    var link133: String?,
    @SerializedName("134")
    var link134: String?,
    @SerializedName("135")
    var link135: String?,
    @SerializedName("136")
    var link136: String?,
    @SerializedName("139")
    var link139: String?,
    @SerializedName("140")
    var link140: String?,
    @SerializedName("160")
    var link160: String?,
    @SerializedName("242")
    var link242: String?,
    @SerializedName("243")
    var link243: String?,
    @SerializedName("244")
    var link244: String?,
    @SerializedName("247")
    var link247: String?,
    @SerializedName("249")
    var link249: String?,
    @SerializedName("250")
    var link250: String?,
    @SerializedName("251")
    var link251: String?,
    @SerializedName("278")
    var link278: String?,
    @SerializedName("394")
    var link394: String?,
    @SerializedName("395")
    var link395: String?,
    @SerializedName("396")
    var link396: String?,
    @SerializedName("397")
    var link397: String?,
    @SerializedName("398")
    var link398: String?
)