package com.example.TheTapGame.presentation.presentation.theme

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.TheTapGame.State.GameState
import com.example.TheTapGame.State.GameType
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

object Util {

    @Composable
    fun formatTime(millis: Long): String {
        val milliseconds = millis % 1000
        return String.format("%04d", milliseconds)
    }

    @Composable
    fun ScoreBar(GameViewModel: MainViewModel){
        val data by GameViewModel.gameState.collectAsState()
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween){
            Text(text = "Score: ${data.score}", fontSize = 20.sp)
            Text(text = "High Score: ${ if(data.highScore != 0L) data.highScore else "N/A"}", fontSize = 20.sp)
            Text(text = "AVG: ${"%.1f".format(data.averageScore)}", fontSize = 20.sp)
        }
        if(data.gameType == GameType.PRECISION){
            Text(text = "Time to target: ${data.targetTime}", fontSize = 20.sp)
        }
    }

    @Composable
    fun StopwatchScreen(GameViewModel: MainViewModel, gameType: GameType) {
        val data = GameViewModel.gameState.collectAsState()
        ComposeClockScreen(GameViewModel, gameType, data)
    }

    @Composable
    fun ComposeClockScreen(GameViewModel: MainViewModel, gameType: GameType, data: State<GameState>) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(.6f)
            .padding(16.dp)) {

            Text(text = formatTime(data.value.timeOnClock), fontSize = 120.sp, modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .clickable {
                    GameViewModel.onEvent(MyEvent.GameStart(gameType))
                })

            ComposeControlButtons(GameViewModel, gameType, data)
        }
    }

    @Composable
    fun ComposeControlButtons(GameViewModel: MainViewModel, gameType: GameType, data: State<GameState>) {
        Row(modifier = Modifier
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically) {

            Button(
                onClick = {
                    GameViewModel.onEvent(MyEvent.GameReset(gameType))
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Yellow,contentColor = Color.Black),
                modifier = Modifier
                    .size(110.dp)
                    .align(Alignment.CenterVertically)
            ) {
                Text("Reset", fontSize = 20.sp)
            }

            Spacer(modifier = Modifier.width(16.dp))

            Button(
                onClick = {
                    GameViewModel.onEvent(MyEvent.GameStart(gameType))
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor =
                    if(data.value.isRunning) Color.Red else Color.Green,
                    contentColor = Color.Black
                ),
                modifier = Modifier
                    .size(110.dp)
                    .align(Alignment.CenterVertically)
            ) {
                Text(
                    if (data.value.isRunning) "STOP" else "Start",
                    fontSize = 20.sp
                )
            }
        }
    }

}