package com.example.mobileterminalsproject

//Jetpack Compose - okhttp3 - Gson
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.lifecycle.LifecycleObserver
import com.google.gson.Gson
import com.google.gson.internal.LinkedTreeMap
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import okhttp3.*
import org.json.JSONObject
import java.io.IOException


var jsonObject: JSONObject? = null
var jsonObjectYT: JSONObject? = null
var mapResponse : Map<String,Any> = HashMap()
var mapResponseYT: Map<String,Any> = HashMap()
var url_var: String = ""
var url_youtube: String = ""
var youTubePlayerView: Any? = null


class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        youTubePlayerView = findViewById<YouTubePlayerView>(R.id.youtube_player_view1)
        lifecycle.addObserver(youTubePlayerView as LifecycleObserver)


        /*
        for(i in 1..30){
            val id = "R.id.youtube_player_view$i"
            //val youtubeListVideos: YouTubePlayerView? = findViewById(id.)
            //val youtubeListVideos: YouTubePlayerView? = findViewById(id.toInt())
            val youtubeListVideos = findViewById<YouTubePlayerView>(id.toInt())
            if (youtubeListVideos != null) {
                lifecycle.addObserver(youtubeListVideos)
            }
        }
         */

    }


    // 'Composable' - Allows function components to be rendered as UI components
    /*
    @Composable
    fun Main() {

        var text by remember {
            mutableStateOf(TextFieldValue(""))
        }

        var text2 by remember {
            mutableStateOf(TextFieldValue(""))
        }

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

                TextField(
                    value = text2,
                    onValueChange = {
                        text2 = it
                    },
                    label = { Text(text = "SEARCH") },
                    placeholder = { Text(text = "WRITE HERE") },
                )

                Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
                    Button(
                        onClick = {
                            url_youtube = "https://www.googleapis.com/youtube/v3/search?key=AIzaSyApV6dplDiNINpBoGFYb3yz45IvpgVzl6E&part=snippet&q=${text2.text}"
                            sendRequestYoutube()
                            setContentView(R.layout.activity_main)

                        }
                    ) {
                        Text(text = "SEARCH")
                    }
                }
            }
        }
    }

     */


    fun beginRequest(view: View) {
        val firstPage = findViewById<LinearLayout>(R.id.first_page)
        val secondPage = findViewById<LinearLayout>(R.id.second_page)
        firstPage.visibility = View.GONE
        secondPage.visibility = View.VISIBLE
    }





    // dato importante --> 'videoId' --> url : https://www.youtube.com/watch?v=videoID
    //                 --> 'immagine del video' --> url associato
    fun sendRequestYoutube(view: View) {

        val firstEditText: EditText = findViewById(R.id.first_edit_text)
        url_youtube = "https://www.googleapis.com/youtube/v3/search?key=AIzaSyApV6dplDiNINpBoGFYb3yz45IvpgVzl6E&part=snippet&q=${firstEditText.text}"
        val boxPlayer : LinearLayout = findViewById(R.id.box_player)
        boxPlayer.visibility = View.VISIBLE

        var firstVideoId: String = ""

        val client = OkHttpClient()
        val request= Request.Builder()
            .url(url_youtube)
            .build()


        client.newCall(request).enqueue(object : Callback {

            override fun onFailure(call: Call, e: IOException) {
                Log.d("OkHttp", "API failed")
            }

            override fun onResponse(call: Call, response: Response){
                if (response.isSuccessful) {

                    response.body?.let {
                        jsonObjectYT = JSONObject(it.string())
                        mapResponseYT = Gson().fromJson(jsonObjectYT.toString(), mapResponseYT.javaClass)

                        firstVideoId = (((mapResponseYT["items"] as ArrayList<*>)[0] as LinkedTreeMap<*,*>)["id"] as LinkedTreeMap<*,*>)["videoId"].toString()
                        println(firstVideoId)
                    }

                    (youTubePlayerView as YouTubePlayerView).addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                        override fun onReady(youTubePlayer: YouTubePlayer) {
                            youTubePlayer.loadVideo(firstVideoId, 0f)
                        }
                    })
                } else {
                    Log.d("OkHttp","API succeeded with null result")
                }
            }
        })
    }


    //"https://youtube-video-download-info.p.rapidapi.com/dl?id=7NK_JOkuSVY"
    fun sendRequest(view: View) {

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
}


