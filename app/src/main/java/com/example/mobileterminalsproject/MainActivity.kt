package com.example.mobileterminalsproject

//Jetpack Compose - okhttp3
import android.os.Bundle
import android.util.Log
//import android.util.Size
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
//import com.example.mobileterminalsproject.data_models_network.ProfileModelApi2
//import com.example.mobileterminalsproject.data_models_network.ProfileModelYoutubeDownloadApi
//import com.fasterxml.jackson.databind.ObjectMapper
import com.google.gson.Gson
import com.google.gson.internal.LinkedTreeMap
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

var jsonObject: JSONObject? = null
var mapResponse : Map<String,Any> = HashMap()
var url_var: String = ""

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Main()
        }
    }

    // 'Composable' - Allows function components to be rendered as UI components
    @Composable
    fun Main() {
        Box(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(Color.Green)
        ) {

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                TopAppBar(
                    elevation = 4.dp,
                    backgroundColor = Color.Black,
                ) {

                }

                Text(
                    text = "API Sample",
                    style = TextStyle(
                        fontSize = 40.sp,
                        fontFamily = FontFamily.Cursive
                    )
                )

                Spacer(modifier = Modifier.height(15.dp))

                Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
                    Button(
                        onClick = {
                            sendRequest()
                        }
                    ) {
                        Text(text = "Get Data")
                    }
                }

                Spacer(modifier = Modifier.height(15.dp))

                var text by remember {
                    mutableStateOf(TextFieldValue(""))
                }

                TextField(
                    value = text,
                    onValueChange = {
                        text = it
                    },
                    label = { Text(text = "ENTER ID SONG") },
                    placeholder = { Text(text = "WRITE HERE") },
                )

                Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
                    Button(
                        onClick = {
                            url_var = "https://youtube-video-download-info.p.rapidapi.com/dl?id=${text.text}"
                            sendRequest()
                        }
                    ) {
                        Text(text = "SEARCH")
                    }
                }
            }
        }
    }

    //"https://youtube-video-download-info.p.rapidapi.com/dl?id=7NK_JOkuSVY"
    private fun sendRequest() {

        val client = OkHttpClient.Builder().build()

        val request = Request.Builder()
            .url(url_var)
            .get()
            .addHeader("X-RapidAPI-Key", "cfbc210f66msh93725be4ee82e18p14e451jsn7fcfa38e4432")
            .addHeader("X-RapidAPI-Host", "youtube-video-download-info.p.rapidapi.com")
            .build()

        client.newCall(request).enqueue(object : Callback {

            override fun onFailure(call: Call, e: IOException) {
                Log.d("OkHttp", "API failed")
            }

            override fun onResponse(call: Call, response: Response){
                if (response.isSuccessful) {

                    response.body?.let {
                        jsonObject = JSONObject(it.string())
                        //val entity = ObjectMapper().readValue(it.string(), ProfileModelYoutubeDownloadApi::class.java)
                        mapResponse = Gson().fromJson(jsonObject.toString(), mapResponse.javaClass)
                    }

                    Log.e("OkHttp", mapResponse["title"].toString())
                    Log.e("OkHttp", mapResponse["link"].toString())

                    val link = mapResponse["link"] as LinkedTreeMap<*,*>
                    for(x in link)
                        Log.e("OkHttp", x.toString())

                } else {
                    Log.d("OkHttp","API succeeded with null result")
                }
            }
        })
    }

    // allows you to have a preview without emulating the device
    @Preview
    @Composable
    fun PreviewMain() {
        Main()
    }
}


