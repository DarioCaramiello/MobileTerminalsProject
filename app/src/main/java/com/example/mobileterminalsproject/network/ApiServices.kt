package com.example.mobileterminalsproject.network


import com.example.mobileterminalsproject.data_models_network.ProfileModelApi1
import com.example.mobileterminalsproject.data_models_network.UserModelApi1


import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

public interface ApiServices {
    @Headers(
        "Accept: application/json"
    )
    @GET("/users/{id}")
    abstract fun getUserById(@Path("id") id: String): Call<UserModelApi1?>?
}