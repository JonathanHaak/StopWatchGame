package com.example.TheTapGame.presentation.presentation.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.TheTapGame.State.GameType

@Composable
fun ReactScreen(navController: NavController, gameViewModel: MainViewModel) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text ="GameThree", modifier = Modifier.align(Alignment.CenterHorizontally))
            Button(onClick = { navController.popBackStack() }) {
                Text("Back to Start")
            }

            GameBox(gameViewModel)
            Button(onClick = { gameViewModel.startStop(GameType.REACT) }) {
                Text("Start Game")
            }
        }
    }
}

@Composable
fun GameBox(gameViewModel: MainViewModel) {
    val colorList = listOf(Color.Yellow, Color.Blue, Color.Red)
    val gameState by gameViewModel.gameState.collectAsState()

    val currentColor = colorList[gameState.colorIndex]

    Box(modifier = Modifier
        .size(200.dp)
        .background(currentColor)
        .clickable {
            gameViewModel.updateClock(GameType.REACT)
        }
    )

    Text(text = "Score: ${gameState.score}", fontSize = 24.sp, textAlign = TextAlign.Center)
}
