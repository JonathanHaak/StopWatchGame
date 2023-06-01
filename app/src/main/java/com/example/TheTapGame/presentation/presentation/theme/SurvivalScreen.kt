package com.example.TheTapGame.presentation.presentation.theme

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.TheTapGame.State.GameType
import com.example.TheTapGame.presentation.presentation.theme.Util.ScoreBar
import com.example.TheTapGame.presentation.presentation.theme.Util.StopwatchScreen

@Composable
fun SurvivalScreen(NavController: NavController, GameViewModel: MainViewModel){
    val gameState = GameViewModel.gameState.collectAsState().value

    Surface(modifier = Modifier.fillMaxSize()){
        Column(modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally){
            ScoreBar(GameViewModel = GameViewModel)
            StopwatchScreen(GameViewModel = GameViewModel, gameType = GameType.SURVIVAL)
            Button(onClick = { NavController.popBackStack() }) {
                Text("Back to Start")
            }
        }
    }
}




