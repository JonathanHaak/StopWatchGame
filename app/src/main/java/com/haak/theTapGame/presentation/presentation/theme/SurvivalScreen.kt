package com.haak.theTapGame.presentation.presentation.theme

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.haak.theTapGame.State.GameType
import com.haak.theTapGame.presentation.presentation.theme.Util.ScoreBar
import com.haak.theTapGame.presentation.presentation.theme.Util.StopwatchScreen

@Composable
fun SurvivalScreen(NavController: NavController, GameViewModel: MainViewModel){
    val gameState = GameViewModel.gameState.collectAsState().value

    Surface(modifier = Modifier.fillMaxSize()){
        Column(modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally){
            Text(text = "Round ${gameState.round}", fontSize = 30.sp, textAlign = TextAlign.Center)
            Text(text = "Death Time: ${gameState.earlyStopThreshold}", fontSize = 30.sp, textAlign = TextAlign.Center)
            ScoreBar(GameViewModel = GameViewModel)
            StopwatchScreen(GameViewModel = GameViewModel, gameType = GameType.SURVIVAL)
            Button(onClick = { NavController.popBackStack()},
                modifier = Modifier.align(Alignment.CenterHorizontally)) {
                Text("Back to Start")
            }
        }
    }
}




