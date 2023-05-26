package com.example.stopwatchgame.presentation.presentation.theme

import android.widget.TextView
import androidx.compose.foundation.layout.Arrangement.Absolute.Center
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun GameTwoScreen(NavController: NavController) {
    Surface(modifier = Modifier.fillMaxSize()){
        Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally){
            Text(text ="GameTwo", modifier = Modifier.align(CenterHorizontally))
            Button(onClick = { NavController.navigate("Start") {
                popUpTo("Start") { inclusive = true }
                launchSingleTop = true
            } }) {
                Text("Back to Start")
            }
        }

    }
}

