package com.example.stopwatchgame.presentation.presentation.theme

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun StartScreen(navController: NavController) {
       Surface(modifier = Modifier.fillMaxSize()){
           Column(modifier = Modifier.fillMaxSize(),
               verticalArrangement = Arrangement.SpaceEvenly,
               horizontalAlignment = Alignment.CenterHorizontally){
               Button(onClick = {
                   navController.navigate("GameOne")
               }){
                   Text("GameOne")
               }

               Button(onClick = {
                   navController.navigate("GameTwo")
               }){
                   Text("GameTwo")
               }

               Button(onClick = {
                   navController.navigate("GameThree")
               }){
                   Text("GameThree")
               }

               Button(onClick = {
                   navController.navigate("GameFour")
               }){
                   Text("GameFour")
               }
               Button(onClick = {
                   navController.navigate("Info")
               }){
                   Text("Info")
               }
           }
       }

}