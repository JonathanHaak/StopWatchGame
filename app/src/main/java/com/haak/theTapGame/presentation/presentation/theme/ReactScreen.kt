package com.haak.theTapGame.presentation.presentation.theme

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.haak.theTapGame.State.GameType
import com.haak.theTapGame.presentation.presentation.theme.Util.ScoreBar

@Composable
fun ReactScreen(navController: NavController, gameViewModel: MainViewModel) {
    val gameState by gameViewModel.gameState.collectAsState()
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            if(gameState.isGameFinished){
                Text(text = "Game Over, press Start to play again", fontSize = 30.sp, modifier = Modifier.align(Alignment.CenterHorizontally))
            }
            else {
                Text(text = "Yellow for Points, Red to live", fontSize = 30.sp, modifier = Modifier.align(Alignment.CenterHorizontally))
            }
            Text(text ="React", fontSize = 50.sp, modifier = Modifier.align(Alignment.CenterHorizontally))
            ScoreBar(gameViewModel)
            Button(onClick = { navController.popBackStack() }) {
                Text("Back to Start")
            }

            GameBox(gameViewModel)
            Button(onClick = { gameViewModel.onEvent(MyEvent.GameStart(GameType.REACT)) }) {
                Text("Start")
            }
        }
    }
}

@Composable
fun GameBox(gameViewModel: MainViewModel) {
    val colorList = listOf(Color.Yellow, Color.Blue, Color.Red, Color.Green, Color.Magenta, Color.Cyan, Color.Gray, Color.Black)
    val gameState by gameViewModel.gameState.collectAsState()

    val currentColor = colorList[gameState.colorIndex]

    Box(modifier = Modifier
        .size(300.dp)
        .background(currentColor)
        .clickable {
            gameViewModel.onEvent(MyEvent.GameStop(GameType.REACT))
        }
    )

    Text(text = "Score: ${gameState.score}", fontSize = 40.sp, textAlign = TextAlign.Center)
}
