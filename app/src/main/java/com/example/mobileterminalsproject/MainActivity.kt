package com.example.mobileterminalsproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

// for Jetpack Compose
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material3.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobileterminalsproject.data_models_network.ProfileModelApi1
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

//Retrofit


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

                val id = remember {
                    mutableStateOf(TextFieldValue())
                }

                val profile = remember {
                    mutableStateOf(
                        ProfileModelApi1(
                            age = "",
                            name = "",
                            email = ""
                        )
                    )
                }



                var response : Response? = null

                Text(
                    text = "API Sample",
                    style = TextStyle(
                        fontSize = 40.sp,
                        fontFamily = FontFamily.Cursive
                    )
                )

                Spacer(modifier = Modifier.height(15.dp))

                TextField(
                    label = { Text(text = "User ID") },
                    value = id.value,
                    onValueChange = { id.value = it }
                )

                Spacer(modifier = Modifier.height(15.dp))

                Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
                    Button(
                        onClick = {
                            val response = sendRequest()
                            /*val data = sendRequest(
                                id = id.value.text,
                                profileState = profile
                             */
                            Log.d("Main Activity", profile.toString())
                        }
                    ) {
                        Text(text = "Get Data")
                    }
                }

                Spacer(modifier = Modifier.height(15.dp))

                //Text(text = profile.component1().toString(), fontSize = 40.sp)
                Text(text = response.toString(), fontSize = 30.sp)

            }
        }
    }


    /*
    private fun sendRequest(
        id: String,
        profileState: MutableState<ProfileModelApi1>
    ) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://192.168.0.109:3000")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(ApiServices::class.java)

        val call: Call<UserModelApi1?>? = api.getUserById(id)

        call!!.enqueue(object: Callback<UserModelApi1?> {
            override fun onResponse(call: Call<UserModelApi1?>, response: Response<UserModelApi1?>) {
                if(response.isSuccessful) {
                    Log.d("Main", "success!" + response.body().toString())
                    profileState.value = response.body()!!.profile
                }
            }

            override fun onFailure(call: Call<UserModelApi1?>, t: Throwable) {
                Log.e("Main", "Failed mate " + t.message.toString())
            }
        })
    }

 */
    private fun sendRequest(): okhttp3.Response {
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

        return client.newCall(request).execute()

    }

    // allows you to have a preview without emulating the device
    @Preview
    @Composable
    fun PreviewMain() {
        Main()
    }
}