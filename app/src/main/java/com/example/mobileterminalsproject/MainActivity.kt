package com.example.mobileterminalsproject

//Jetpack Compose - okhttp3
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.TopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobileterminalsproject.data_models_network.ProfileModelApi2
import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

var x : JSONObject? = null

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
            }
        }
    }


    private fun sendRequest() {
        val client = OkHttpClient()

        val request = Request.Builder()
            .url("https://free-nba.p.rapidapi.com/teams/1")
            .get()
            .addHeader("X-RapidAPI-Key", "959aee617dmsh51ae45fcec13ee1p18df9fjsnb033b4a917d7")
            .addHeader("X-RapidAPI-Host", "free-nba.p.rapidapi.com")
            .build()

        client.newCall(request).enqueue(object : Callback {

            override fun onFailure(call: Call, e: IOException) {
                Log.d("OkHttp", "Api Fallita")
            }

            override fun onResponse(call: Call, response: Response){
                if (response.isSuccessful) {
                    response.body?.let {
                        x = JSONObject(it.string())
                        Log.d("OkHttp", x.toString())
                    }

                    val entity = ObjectMapper().readValue(x.toString(), ProfileModelApi2::class.java)
                    Log.d("OkHttp", entity.city.toString())

                } else {
                    Log.d("OkHttp","Api Riuscita - Null")
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