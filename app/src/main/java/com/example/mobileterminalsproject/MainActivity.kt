package com.example.mobileterminalsproject

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.LifecycleObserver
import com.google.gson.Gson
import com.google.gson.internal.LinkedTreeMap
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

private var jsonObject: JSONObject? = null
private var jsonObjectYT: JSONObject? = null
private var mapResponse : Map<String,Any> = HashMap()
private var mapResponseYT: Map<String,Any> = HashMap()
private var url_var: String = ""
private var url_youtube: String = ""
private var videoIdList: MutableList<String> = mutableListOf()
private var pastChoice: String = ""
private var firstExecute: Boolean = true
// 0 -> firstKey  - 1 -> secondKey
private var flagKey: Int = 1
private const val firstKeyYouTube: String = "AIzaSyApV6dplDiNINpBoGFYb3yz45IvpgVzl6E"
private const val secondKeyYouTube: String = "AIzaSyBGtNcpfb8yLAAxKGIOMJjr0XqKx_glgkU"

class MainActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSpinner()
    }

    fun sendRequestYoutube(view: View) {

        val progressBar: ProgressBar = findViewById(R.id.loading_spinner)
        progressBar.visibility = View.VISIBLE

        setFirstExecute()

        val textSpinnerSelection = getSpinnerChoice()
        pastChoice = textSpinnerSelection

        setURL(textSpinnerSelection)


        (findViewById<NestedScrollView>(R.id.scroll_view)).visibility = View.VISIBLE

        val client = OkHttpClient()
        val request= Request.Builder()
            .url(url_youtube)
            .build()

        client.newCall(request).enqueue(object : Callback {

            override fun onFailure(call: Call, e: IOException) {
                progressBar.visibility = View.INVISIBLE
                Log.d("OkHttp", "API failed")
            }

            override fun onResponse(call: Call, response: Response){
                if (response.isSuccessful) {
                    videoIdList = mutableListOf()
                    response.body?.let {
                        //converting the string of the body in JSON Object
                        mapResponse(textSpinnerSelection.toInt(),it)
                    }
                    createPlayerVideos(textSpinnerSelection.toInt())
                    progressBar.visibility = View.INVISIBLE
                } else {
                    progressBar.visibility = View.INVISIBLE
                    changeKeyYouTube()
                    sendRequestYoutube(view)
                }
            }
        })
    }

    private fun sendRequestDownloadLink(i: Int) {

        url_var = "https://youtube-video-download-info.p.rapidapi.com/dl?id=${videoIdList[i-1]}"

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
                    }

                    setLinks()

                    //for changing the views from the main thread
                    runOnUiThread {
                        fromSecondToThirdPage()
                    }
                } else {
                    Log.d("OkHttp","API succeeded with null result")
                }
            }

        })
    }

    private fun setFirstExecute() {
        if(!firstExecute)
            cleanLayout()
        else
            firstExecute = false
    }

    fun createPlayerVideos(numVideos: Int) {
        runOnUiThread {
            //val linearLayoutYoutube = findViewById<LinearLayout>(R.id.box_player)
            for (i in 1..numVideos) {
                singleVideoCreation(findViewById(R.id.box_player), i)
                createButtons(i)
            }
        }
    }

    private fun createButtons(i: Int) {
        val customFontPath = "fonts/opensans_semibold.ttf"
        val customFont = Typeface.createFromAsset(this.assets, customFontPath)
        val linearLayoutYoutube = findViewById<LinearLayout>(R.id.box_player)
        val button = Button(this)

        // set button layout parameters
        button.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        button.background = ResourcesCompat.getDrawable(this.resources, R.drawable.rounded_corner, null)
        val layoutParams = button.layoutParams as ViewGroup.MarginLayoutParams
        layoutParams.setMargins(20, 20, 20, 20) // set margins to 20dp on all sides
        button.layoutParams = layoutParams
        button.id = i
        button.width = 400
        button.text = resources.getText(R.string.Download)
        button.backgroundTintList = ColorStateList.valueOf(Color.rgb(32,191,85))
        button.typeface = customFont
        button.setTextColor(ContextCompat.getColor(this, R.color.white))
        button.textSize = 14F
        button.setOnClickListener {
            sendRequestDownloadLink(i)
        }
        linearLayoutYoutube.addView(button)
    }

    private fun singleVideoCreation(linearLayoutYoutube: LinearLayout, i: Int) {
        val video = YouTubePlayerView(this)
        video.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        val layoutParams = video.layoutParams as ViewGroup.MarginLayoutParams
        layoutParams.setMargins(50, 50, 50, 50) // set margins to 20dp on all sides
        video.layoutParams = layoutParams
        video.id = i + 100
        linearLayoutYoutube.addView(video)

        lifecycle.addObserver(video as LifecycleObserver)
        video.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                youTubePlayer.cueVideo(videoIdList[i - 1], 0f)
            }
        })
    }

    @SuppressLint("SetTextI18n")
    private fun setLinks() {
        mapResponse = Gson().fromJson(jsonObject.toString(), mapResponse.javaClass)
        val link = mapResponse["link"] as? LinkedTreeMap<*,*>

        //setting the links in the text if they are not null
        if( (link?.get("18") as? ArrayList<*>)?.get(0).toString()!= "null") {
            findViewById<TextView>(R.id.first_link).text = (link?.get("18") as? ArrayList<*>)?.get(0).toString()
        } else
            findViewById<TextView>(R.id.first_link).text = "Link not available"

        if( (link?.get("22") as? ArrayList<*>)?.get(0).toString()!= "null") {
            findViewById<TextView>(R.id.second_link).text = (link?.get("22") as? ArrayList<*>)?.get(0).toString()
        } else
            findViewById<TextView>(R.id.second_link).text = "Link not available"

        if( (link?.get("140") as? ArrayList<*>)?.get(0).toString() != "null") {
            findViewById<TextView>(R.id.third_link).text = (link?.get("140") as? ArrayList<*>)?.get(0).toString()
        } else
            findViewById<TextView>(R.id.third_link).text = "Link not available"

        if( (link?.get("251") as? ArrayList<*>)?.get(0).toString() != "null") {
            findViewById<TextView>(R.id.fourth_link).text = (link?.get("251") as? ArrayList<*>)?.get(0).toString()
        } else
            findViewById<TextView>(R.id.fourth_link).text = "Link not available"
    }

    private fun cleanLayout() {
        for(i in 1..pastChoice.toInt()) {
            val linearLayoutYoutube = findViewById<LinearLayout>(R.id.box_player)
            val videoPlayerRemove = findViewById<YouTubePlayerView>(i+100)
            val buttonToRemove = findViewById<Button>(i)
            linearLayoutYoutube.removeView(videoPlayerRemove)
            linearLayoutYoutube.removeView(buttonToRemove)
        }
    }

    private fun setURL(textSpinnerSelection: String) {
        // key expiration management for request on YouTube
        url_youtube = if(flagKey==1)
            "https://www.googleapis.com/youtube/v3/search?key=$firstKeyYouTube&part=snippet&maxResults=$textSpinnerSelection&q=${findViewById<EditText>(R.id.first_edit_text).text}"
        else
            "https://www.googleapis.com/youtube/v3/search?key=$secondKeyYouTube&part=snippet&maxResults=$textSpinnerSelection&q=${findViewById<EditText>(R.id.first_edit_text).text}"
    }

    fun hidePageDownloadLink(view: View) {
        findViewById<LinearLayout>(R.id.second_page).visibility = View.VISIBLE
        findViewById<NestedScrollView>(R.id.scroll_view).visibility = View.VISIBLE
        findViewById<LinearLayout>(R.id.thirdPage).visibility = View.GONE
    }

    fun fromFirstToSecondPage(view: View) {
        findViewById<LinearLayout>(R.id.first_page).visibility = View.GONE
        findViewById<LinearLayout>(R.id.second_page).visibility = View.VISIBLE
    }

    private fun fromSecondToThirdPage() {
        findViewById<LinearLayout>(R.id.second_page).visibility = View.GONE
        findViewById<NestedScrollView>(R.id.scroll_view).visibility = View.INVISIBLE
        findViewById<LinearLayout>(R.id.thirdPage).visibility = View.VISIBLE
    }

    private fun setSpinner(){
        //set the spinner adapter
        val spinner: Spinner = findViewById(R.id.spinner)

        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.results,
            R.layout.spinner_item
        )

        adapter.setDropDownViewResource(R.layout.spinner_selected_item)

        spinner.adapter = adapter
    }

    private fun getSpinnerChoice() : String {
        return findViewById<Spinner>(R.id.spinner).selectedItem as String
    }

    private fun mapResponse(textSpinnerSelection: Int, it : ResponseBody) {
        // var flag: Boolean = false
        jsonObjectYT = JSONObject(it.string())
        //mapping the JSON Object in a structure that follows the JSON object
        mapResponseYT = Gson().fromJson(jsonObjectYT.toString(), mapResponseYT.javaClass)
        //extracting all video ids and adding them to a list
        val items = mapResponseYT["items"] as ArrayList<*>
        //contains indexes where in 'videoIdList' is null
        val listOfNull  = mutableListOf<Int>()

        for(i in 0 until textSpinnerSelection) {
            videoIdList.add((((items[i] as LinkedTreeMap<*, *>)["id"] as LinkedTreeMap<*, *>)["videoId"]).toString())
            if(videoIdList[i] == "null")
                listOfNull.add(i)
        }
        if(listOfNull.isNotEmpty() && textSpinnerSelection!=50) {

            var newTextSpinnerSelection = textSpinnerSelection

            newTextSpinnerSelection += if(textSpinnerSelection==5)
                5
            else
                10

            val client = OkHttpClient()
            val request = Request.Builder()
                .url("https://www.googleapis.com/youtube/v3/search?key=$firstKeyYouTube&part=snippet&maxResults=${newTextSpinnerSelection}&q=${findViewById<EditText>(R.id.first_edit_text).text}")
                .build()

            client.newCall(request).enqueue(object : Callback {

                override fun onFailure(call: Call, e: IOException) {
                    Log.d("OkHttp", "API failed")
                }

                override fun onResponse(call: Call, response: Response){
                    if (response.isSuccessful) {
                        var jsonObjectYTSecond: JSONObject?
                        var mapResponseYTSecond: Map<String,Any> = HashMap()

                        response.body?.let {
                            jsonObjectYTSecond = JSONObject(it.string())
                            mapResponseYTSecond = Gson().fromJson(jsonObjectYTSecond.toString(), mapResponseYTSecond.javaClass)
                            val itemsRunTime = mapResponseYTSecond["items"] as ArrayList<*>
                            val countNull = listOfNull.size
                            var countRunTimeNull = 0

                            for(i in textSpinnerSelection until newTextSpinnerSelection) {
                                videoIdList[listOfNull[countRunTimeNull]] = (((itemsRunTime[i] as LinkedTreeMap<*, *>)["id"] as LinkedTreeMap<*, *>)["videoId"]).toString()
                                countRunTimeNull+=1
                                if(countRunTimeNull==countNull)
                                    break
                            }

                            for(i in 0 until textSpinnerSelection)
                                println(videoIdList[i])
                        }

                    } else {
                        Log.d("OkHttp", "API response is null")
                        changeKeyYouTube()
                    }
                }
            })
        }
    }

    private fun changeKeyYouTube() {
        flagKey = if(flagKey==1)
            0
        else
            1
    }
}
