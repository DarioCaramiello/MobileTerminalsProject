package com.example.mobileterminalsproject

//Jetpack Compose - okhttp3 - Gson
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ScrollView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.LifecycleObserver
import com.google.gson.Gson
import com.google.gson.internal.LinkedTreeMap
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerCallback
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
const val defaultId = "p2vpqKBPj4U"
val listView = ArrayList<YouTubePlayerView>()
val checkForButtonDownload : MutableList<Boolean> = mutableListOf()
val videoIdList: MutableList<String> = mutableListOf()



class MainActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView.add(findViewById(R.id.youtube_player_view1))
        listView.add(findViewById(R.id.youtube_player_view2))
        listView.add(findViewById(R.id.youtube_player_view3))
        listView.add(findViewById(R.id.youtube_player_view4))
        listView.add(findViewById(R.id.youtube_player_view5))

        for(i in 0..4)
            checkForButtonDownload.add(false)

        for(i in 1..5) {
            val id= getResources().getIdentifier("youtube_player_view$i", "id", getPackageName())
            val youtubeListVideos = findViewById<YouTubePlayerView>(id)

            lifecycle.addObserver(youtubeListVideos as LifecycleObserver)

            youtubeListVideos.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    youTubePlayer.cueVideo(defaultId, 0f)
                }
            })
        }
    }

    fun beginRequest(view: View) {
        val firstPage = findViewById<LinearLayout>(R.id.first_page)
        val secondPage = findViewById<LinearLayout>(R.id.second_page)
        firstPage.visibility = View.GONE
        secondPage.visibility = View.VISIBLE
    }


    // key Simone : key=AIzaSyApV6dplDiNINpBoGFYb3yz45IvpgVzl6E
    // key Dario : key=AIzaSyBGtNcpfb8yLAAxKGIOMJjr0XqKx_glgkU
    fun sendRequestYoutube(view: View) {
        val firstEditText: EditText = findViewById(R.id.first_edit_text)
        url_youtube = "https://www.googleapis.com/youtube/v3/search?key=AIzaSyBGtNcpfb8yLAAxKGIOMJjr0XqKx_glgkU&part=snippet&q=${firstEditText.text}"
        (findViewById<NestedScrollView>(R.id.scroll_view)).visibility = View.VISIBLE

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

                        val items = mapResponseYT["items"] as ArrayList<*>
                        for(i in 0..4) {
                            val itemsVal = items[i] as LinkedTreeMap<*,*>
                            val idVal = itemsVal["id"] as LinkedTreeMap<*,*>
                            val final = idVal["videoId"].toString()
                            videoIdList.add(final)
                        }
                    }

                    var count = 0
                    for(i in listView) {
                        i.getYouTubePlayerWhenReady(object :YouTubePlayerCallback {
                            override fun onYouTubePlayer(youTubePlayer: YouTubePlayer) {
                                youTubePlayer.cueVideo(videoIdList[count], 0f)
                                count++
                            }
                        })
                    }
                } else {
                    Log.d("OkHttp","API succeeded with null result")
                }
            }
        })
    }

    fun checkAndSendRequest1(view: View){
        checkForButtonDownload[0]=true
        sendRequest()
    }

    fun checkAndSendRequest2(view: View) {
        checkForButtonDownload[1]=true
        sendRequest()
    }

    fun checkAndSendRequest3(view: View) {
        checkForButtonDownload[2]=true
        sendRequest()
    }

    fun checkAndSendRequest4(view: View) {
        checkForButtonDownload[3]=true
        sendRequest()
    }

    fun checkAndSendRequest5(view: View){
        checkForButtonDownload[4]=true
        sendRequest()
    }


    //"https://youtube-video-download-info.p.rapidapi.com/dl?id=7NK_JOkuSVY"
    private fun sendRequest() {

        for(i in 0..4) {
            if(checkForButtonDownload[i]) {
                url_var = "https://youtube-video-download-info.p.rapidapi.com/dl?id=${videoIdList[i]}"
                checkForButtonDownload[i]=false
                break
            }
        }

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

                    /*
                    for(x in link)
                        Log.e("OkHttp", x.toString())

                     */

                    val link = mapResponse["link"] as LinkedTreeMap<*,*>
                    val linkDownload1 = link["17"] as ArrayList<*>
                    val linkDownload2 = link["18"] as ArrayList<*>
                    val linkDownload3 = link["22"] as ArrayList<*>

                    println(linkDownload1[0].toString())
                    println(linkDownload1[1].toString())
                    println(linkDownload1[2].toString())
                    println("-------------------------------")
                    println(linkDownload2[0].toString())
                    println(linkDownload2[1].toString())
                    println(linkDownload2[2].toString())
                    println("-------------------------------")
                    println(linkDownload3[0].toString())
                    println(linkDownload3[1].toString())
                    println(linkDownload3[2].toString())


                } else {
                    Log.d("OkHttp","API succeeded with null result")
                }
            }
        })
    }
}


