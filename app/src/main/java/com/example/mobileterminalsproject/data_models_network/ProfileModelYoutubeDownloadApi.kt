package com.example.mobileterminalsproject.data_models_network

import com.google.firebase.firestore.IgnoreExtraProperties
import com.google.gson.annotations.SerializedName
import java.lang.reflect.Constructor

data class ProfileModelYoutubeDownloadApi (
    @SerializedName("id")
    var id: String? = "",
    //@SerializedName("allowRatings")
    var allowRatings: String? = "",
    @SerializedName("isPrivate")
    var isPrivate: String? = "",
    @SerializedName("isUnpluggedCorpus")
    var isUnpluggedCorpus: String? = "",
    //@SerializedName("isLiveContent")
    var isLiveContent: String? = "",
    @SerializedName("title")
    var title: String? = "",
    @SerializedName("thumb")
    var thumb: String? = "",
    @SerializedName("length")
    var length: String? = "",
    @SerializedName("lengthsec")
    var lengthsec: String? = "",
    @SerializedName("keywords")
    var keywords: String? = "",
    @SerializedName("avg_rating")
    var avg_rating: String? = "",
    @SerializedName("author")
    var author: String? = "",
    @SerializedName("channelid")
    var channelid: String? = "",
    @SerializedName("username")
    var username: String? = "",
    @SerializedName("view_count")
    var view_count: String? = "",
    @SerializedName("date")
    var date: String? = "",
    @SerializedName("description")
    var description: String? = "",
    @SerializedName("category")
    var category: String? = "",
    @SerializedName("link")
    var link: List<ProfileModelYoutubeLinkApi> = emptyList(),
    @SerializedName("msg")
    var msg: String? = "",
    @SerializedName("status")
    var status: String? = "",
    @SerializedName("cleantitle")
    var cleantitle: String? = "",
    @SerializedName("related")
    var related: String? = "",
    @SerializedName("altserver")
    var altserver: String? = "",
    @SerializedName("lexp")
    var lexp: String? = "",
    @SerializedName("age")
    var age: String? = ""
        )

data class ProfileModelYoutubeLinkApi(
    @SerializedName("17")
    var link17: ProfileModelYoutubeLinkClass,
    @SerializedName("18")
    var link18: ProfileModelYoutubeLinkClass,
    @SerializedName("22")
    var link22: ProfileModelYoutubeLinkClass,
    @SerializedName("133")
    var link133: ProfileModelYoutubeLinkClass,
    @SerializedName("134")
    var link134: ProfileModelYoutubeLinkClass,
    @SerializedName("135")
    var link135: ProfileModelYoutubeLinkClass,
    @SerializedName("136")
    var link136: ProfileModelYoutubeLinkClass,
    @SerializedName("139")
    var link139: ProfileModelYoutubeLinkClass,
    @SerializedName("140")
    var link140: ProfileModelYoutubeLinkClass,
    @SerializedName("160")
    var link160: ProfileModelYoutubeLinkClass,
    @SerializedName("242")
    var link242: ProfileModelYoutubeLinkClass,
    @SerializedName("243")
    var link243: ProfileModelYoutubeLinkClass,
    @SerializedName("244")
    var link244: ProfileModelYoutubeLinkClass,
    @SerializedName("247")
    var link247: ProfileModelYoutubeLinkClass,
    @SerializedName("249")
    var link249: ProfileModelYoutubeLinkClass,
    @SerializedName("250")
    var link250: ProfileModelYoutubeLinkClass,
    @SerializedName("251")
    var link251: ProfileModelYoutubeLinkClass,
    @SerializedName("278")
    var link278: ProfileModelYoutubeLinkClass,
    @SerializedName("394")
    var link394: ProfileModelYoutubeLinkClass,
    @SerializedName("395")
    var link395: ProfileModelYoutubeLinkClass,
    @SerializedName("396")
    var link396: ProfileModelYoutubeLinkClass,
    @SerializedName("397")
    var link397: ProfileModelYoutubeLinkClass,
    @SerializedName("398")
    var link398: ProfileModelYoutubeLinkClass
)

data class ProfileModelYoutubeLinkClass(
    @SerializedName("0")
    var effectiveLink: String? = "",
    @SerializedName("1")
    var sizeMb: String? = "",
    @SerializedName("2")
    var info: String? = "",
    @SerializedName("3")
    var quality: String? = "",
    @SerializedName("4")
    var type: String? = ""
)