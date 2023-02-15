package com.example.mobileterminalsproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Main()
        }
    }

    // 'Composable' - permette ai componenti della funzione di visualizzare come componenti dell UI
    @Composable
    fun Main() {
        Box(
            Modifier
                .fillMaxSize()) {

            Box(
                Modifier
                    .align(Alignment.Center)
                    .height(40.dp)
                    .fillMaxWidth()
                    .background(Color.White)) {
                Box(
                    Modifier
                        .align(Alignment.Center)) {
                    Text(text = "API")
                }

            }

            Box(
                Modifier
                    .height(50.dp)
                    .fillMaxWidth()
                    .background(Color.LightGray)) {
                Box(
                    Modifier
                        .align(Alignment.Center)) {
                    Text(text = "ANDROID")
                }

            }
        }
    }

    // permette di avere una preview senza emulare il device
    @Preview
    @Composable
    fun PreviewMain() {
        Main()
    }
}