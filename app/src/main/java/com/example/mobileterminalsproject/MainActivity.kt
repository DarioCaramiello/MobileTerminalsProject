package com.example.mobileterminalsproject

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
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
var videoIdList: MutableList<String> = mutableListOf()
var prova: LinearLayout? = null

class MainActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        prova = findViewById(R.id.box_player)
        
/*
        for (i in 0..4)
            checkForButtonDownload.add(false)
 */
    }

    fun beginRequest(view: View) {
        findViewById<LinearLayout>(R.id.first_page).visibility = View.GONE
        findViewById<LinearLayout>(R.id.second_page).visibility = View.VISIBLE
    }

    // key Simone : key=AIzaSyApV6dplDiNINpBoGFYb3yz45IvpgVzl6E
    // key Dario : key=AIzaSyBGtNcpfb8yLAAxKGIOMJjr0XqKx_glgkU
    fun sendRequestYoutube(view: View) {

        //setContentView(findViewById(R.id.box_player))
        val radioGroup = findViewById<RadioGroup>(R.id.radio_group_choice)
        val idButtonRadio = radioGroup.checkedRadioButtonId
        val buttonRadio = findViewById<RadioButton>(idButtonRadio)
        val textButtonRadio = buttonRadio.text.toString()
        (findViewById<NestedScrollView>(R.id.scroll_view)).visibility = View.VISIBLE


        val firstEditText: EditText = findViewById(R.id.first_edit_text)
        url_youtube = "https://www.googleapis.com/youtube/v3/search?key=AIzaSyBGtNcpfb8yLAAxKGIOMJjr0XqKx_glgkU&part=snippet&maxResults=$textButtonRadio&q=${firstEditText.text}"


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
                    videoIdList = mutableListOf()
                    response.body?.let {
                        //converting the string of the body in JSON Object
                        jsonObjectYT = JSONObject(it.string())
                        //mapping the JSON Object
                        mapResponseYT = Gson().fromJson(jsonObjectYT.toString(), mapResponseYT.javaClass)

                        //extracting all video ids and adding them to a list
                        val items = mapResponseYT["items"] as ArrayList<*>


                        for(i in 0 until textButtonRadio.toInt()) {
                            videoIdList.add((((items[i] as LinkedTreeMap<*, *>)["id"] as LinkedTreeMap<*, *>)["videoId"]).toString())
                        }


                        //createPlayerVideos(prova, textButtonRadio.toInt())
                        createPlayerVideos(textButtonRadio.toInt())

                        /*
                        //loop that sets the Youtube API
                        for (i in 1..textButtonRadio.toInt()) {
                            //for adding to the list of youtube videos each youtube player view (box for the youtube video).
                            //using getIdentifier for finding the view with the string letting us cycle through the
                            //views with an iterator.
                            //val id = resources.getIdentifier("$i", "id", packageName)
                            val youtubeListVideos = findViewById<YouTubePlayerView>(i+10)

                            lifecycle.addObserver(youtubeListVideos as LifecycleObserver)

                            youtubeListVideos.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                                override fun onReady(youTubePlayer: YouTubePlayer) {
                                    youTubePlayer.cueVideo(defaultId, 0f)
                                }
                            })
                        }

                         */

                        /*

                        for(i in 1..textButtonRadio.toInt()) {
                            //val id = resources.getIdentifier("${i+10}", "id", packageName)
                            //listView.add(findViewById(i+10))
                            findViewById<YouTubePlayerView>(i+10).getYouTubePlayerWhenReady(object :YouTubePlayerCallback {
                                override fun onYouTubePlayer(youTubePlayer: YouTubePlayer) {
                                    youTubePlayer.cueVideo(findViewById<YouTubePlayerView>(i+10).toString(), 0f)
                                    //count++
                                }
                            })
                        }


                         */

                        /*
                        listView.add(findViewById(R.id.youtube_player_view1))
                        listView.add(findViewById(R.id.youtube_player_view2))
                        listView.add(findViewById(R.id.youtube_player_view3))
                        listView.add(findViewById(R.id.youtube_player_view4))
                        listView.add(findViewById(R.id.youtube_player_view5))
                        */
                    }


                    //setting the right ids for each Youtube player

                    /*
                    var count = 1
                    for(i in listView) {
                        i.getYouTubePlayerWhenReady(object :YouTubePlayerCallback {
                            override fun onYouTubePlayer(youTubePlayer: YouTubePlayer) {
                                youTubePlayer.cueVideo(videoIdList[count], 0f)
                                count++
                            }
                        })
                    }

                     */



                } else {
                    Log.d("OkHttp","API succeeded with null result")
                }
            }
        })
    }


    /*
    //functions for every download button under each video that sets a boolean letting us know
    // witch videoId must be sent to the link download API
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

     */


    //"https://youtube-video-download-info.p.rapidapi.com/dl?id=7NK_JOkuSVY"
    private fun sendRequest(i: Int) {
        /*
        //setting the right url + videoId based on the download button corresponding the video
        //we want to download.
        for(i in 0..4) {
            if(checkForButtonDownload[i]) {
                url_var = "https://youtube-video-download-info.p.rapidapi.com/dl?id=${videoIdList[i]}"
                checkForButtonDownload[i]=false
                break
            }
        }
         */

        url_var = "https://youtube-video-download-info.p.rapidapi.com/dl?id=$i"

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

            @SuppressLint("SetTextI18n")
            override fun onResponse(call: Call, response: Response){
                if (response.isSuccessful) {

                    response.body?.let {
                        jsonObject = JSONObject(it.string())
                        mapResponse = Gson().fromJson(jsonObject.toString(), mapResponse.javaClass)
                    }

                    val link = mapResponse["link"] as? LinkedTreeMap<*,*>


                    if( (link?.get("18") as? ArrayList<*>)?.get(0).toString()!= "null")
                        findViewById<TextView>(R.id.first_link).text = (link?.get("18") as? ArrayList<*>)?.get(0).toString()
                    else
                        findViewById<TextView>(R.id.first_link).text = "Link not available"

                    if( (link?.get("22") as? ArrayList<*>)?.get(0).toString()!= "null")
                        findViewById<TextView>(R.id.second_link).text = (link?.get("22") as? ArrayList<*>)?.get(0).toString()
                    else
                        findViewById<TextView>(R.id.second_link).text = "Link not available"

                    if( (link?.get("140") as? ArrayList<*>)?.get(0).toString() != "null")
                        findViewById<TextView>(R.id.third_link).text = (link?.get("140") as? ArrayList<*>)?.get(0).toString()
                    else
                        findViewById<TextView>(R.id.third_link).text = "Link not available"

                    if( (link?.get("251") as? ArrayList<*>)?.get(0).toString() != "null")
                        findViewById<TextView>(R.id.fourth_link).text = (link?.get("251") as? ArrayList<*>)?.get(0).toString()
                    else
                        findViewById<TextView>(R.id.fourth_link).text = "Link not available"


                    runOnUiThread {
                        findViewById<LinearLayout>(R.id.second_page).visibility = View.GONE
                        findViewById<NestedScrollView>(R.id.scroll_view).visibility = View.INVISIBLE
                        findViewById<LinearLayout>(R.id.thirdPage).visibility = View.VISIBLE
                    }
                } else {
                    Log.d("OkHttp","API succeeded with null result")
                }
            }
        })
    }

    fun hidePageDownloadLink(view: View) {
        findViewById<LinearLayout>(R.id.second_page).visibility = View.VISIBLE
        findViewById<NestedScrollView>(R.id.scroll_view).visibility = View.VISIBLE
        findViewById<LinearLayout>(R.id.thirdPage).visibility = View.GONE
    }

    
    fun createPlayerVideos(numVideos: Int) {

        runOnUiThread {
            val prova = findViewById<LinearLayout>(R.id.box_player)
            for (i in 1..numVideos) {
                val video = YouTubePlayerView(this)
                video.layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )

                val layoutParams = video.layoutParams as ViewGroup.MarginLayoutParams
                layoutParams.setMargins(20, 20, 20, 20) // set margins to 20dp on all sides
                video.layoutParams = layoutParams
                video.id = i + 10
                prova.addView(video)


                //val youtubeListVideos = findViewById<YouTubePlayerView>(video.id)

                lifecycle.addObserver(video as LifecycleObserver)


                video.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                    override fun onReady(youTubePlayer: YouTubePlayer) {
                        youTubePlayer.cueVideo(videoIdList[i - 1], 0f)
                    }
                })



                /*
                video.getYouTubePlayerWhenReady(object :YouTubePlayerCallback {
                    override fun onYouTubePlayer(youTubePlayer: YouTubePlayer) {
                        youTubePlayer.cueVideo(video.id.toString(), 0f)
                    }
                })

                 */


                createButtons(i)
            }
        }
        /*
        // Get a reference to the main thread's Looper
        val mainLooper = Looper.getMainLooper()
        // Create a Handler using the main thread's Looper
        val handler = Handler(mainLooper)
        // Create the WebView on the main thread using the Handler
        handler.post(Runnable {
            for(i in 1..numVideos) {
                val video = YouTubePlayerView(this)
                video.layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )

                val layoutParams = video.layoutParams as ViewGroup.MarginLayoutParams
                layoutParams.setMargins(20, 20, 20, 20) // set margins to 20dp on all sides
                video.layoutParams = layoutParams
                video.id = i+10
                createButtons(view,i)
            }
        })

         */
    }

    fun createButtons(i: Int) {
        val prova = findViewById<LinearLayout>(R.id.box_player)
        val button = Button(this)
        // set button layout parameters
        button.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        button.id = i
        button.text = "Download"
        button.setBackgroundColor(ContextCompat.getColor(this, R.color.light_green))
        button.setTypeface(null, Typeface.BOLD)
        button.setShadowLayer(4F,4F,2F, R.color.white)
        button.setTextColor(ContextCompat.getColor(this, R.color.black))
        button.textSize = 14F
        button.setOnClickListener {
            sendRequest(i)
        }
        prova.addView(button)
    }
}




