package com.example.mobileterminalsproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

//Jetpack Compose
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material3.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
//okhttp3
import okhttp3.*
import java.io.IOException


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

                //Text(text = profile.component1().toString(), fontSize = 40.sp)
                //Text(text = response.toString(), fontSize = 30.sp)

            }
        }
    }

    private fun sendRequest() {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://daily-atmosphere-carbon-dioxide-concentration.p.rapidapi.com/api/co2-api")
            .get()
            .addHeader("X-RapidAPI-Key", "01028bfd38msh891033d63ee7bedp1b2c18jsn50e742c22515")
            .addHeader(
                "X-RapidAPI-Host",
                "daily-atmosphere-carbon-dioxide-concentration.p.rapidapi.com"
            )
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d("OkHttp", "Api Fallita")
            }

            override fun onResponse(call: Call, response: Response){
                if (response.isSuccessful) {
                    Log.d("OkHttp", response.toString())
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