package com.example.stopwatchgame.presentation.presentation.theme

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
fun InfoScreen(NavController: NavController) {
    Surface(modifier = Modifier.fillMaxSize()){
        Column(modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally){
            Text(text ="This is the Info Screen!", modifier = Modifier
                .align(CenterHorizontally))
            Button(onClick = { NavController.navigate("Start") {
                popUpTo("Start") { inclusive = true }
                launchSingleTop = true
            } }) {
                Text("Back to Start")
            }
        }

    }
}