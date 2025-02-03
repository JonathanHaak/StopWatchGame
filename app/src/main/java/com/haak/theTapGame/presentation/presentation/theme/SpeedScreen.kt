package com.haak.theTapGame.presentation.presentation.theme

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.haak.theTapGame.State.GameType
import com.haak.theTapGame.presentation.presentation.theme.Util.ScoreBar
import com.haak.theTapGame.presentation.presentation.theme.Util.StopwatchScreen

@Composable
fun SpeedScreen(NavController: NavController, GameViewModel: MainViewModel){
    Surface(modifier = Modifier.fillMaxSize()){
        Column(modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally){
            ScoreBar(GameViewModel = GameViewModel)
            StopwatchScreen(GameViewModel = GameViewModel, gameType = GameType.SPEED)
            Button(onClick = { NavController.popBackStack() }) {
                Text("Back to Start")
            }
        }
    }
}



