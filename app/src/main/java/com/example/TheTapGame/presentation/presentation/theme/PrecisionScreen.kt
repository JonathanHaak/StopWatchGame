package com.example.TheTapGame.presentation.presentation.theme

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
import com.example.TheTapGame.State.GameType

@Composable
fun PrecisionScreen(NavController: NavController, GameViewModel: MainViewModel){
    Surface(modifier = Modifier.fillMaxSize()){
        Column(modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally){
            Text(text = "Precision", modifier = Modifier
                .align(CenterHorizontally))
            Util.ScoreBar(GameViewModel = GameViewModel)
            Util.StopwatchScreen(GameViewModel = GameViewModel, gameType = GameType.PRECISION)
            Button(onClick = { NavController.popBackStack() }) {
                Text("Back to Start")
            }
        }
    }
}
