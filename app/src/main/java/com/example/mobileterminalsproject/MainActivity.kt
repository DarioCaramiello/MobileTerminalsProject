package com.example.mobileterminalsproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

// for Jetpack Compose
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
import androidx.compose.ui.unit.sp


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

        // Hierarchy Root Box - Box that encompasses all of the UI
        Box(

            Modifier
                .background(Color.LightGray)
                .fillMaxSize()) {

            // Component UI for API
            Box(
                Modifier
                    .align(Alignment.Center)
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .background(Color.White)) {
                Box(
                    Modifier
                        .align(Alignment.Center)) {
                    Text(
                        text = "API",
                        color = Color.Red,
                        fontSize = 32.sp
                    )
                }
            }

            // Component UI for project name
            Box(
                Modifier
                    .height(50.dp)
                    .fillMaxWidth()
                    .background(Color.Green)) {
                Box(
                    Modifier
                        .align(Alignment.Center)) {
                    Text(text = "ANDROID")
                }

            }
        }
    }

    // allows you to have a preview without emulating the device
    @Preview
    @Composable
    fun PreviewMain() {
        Main()
    }
}