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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

object Util {

    @Composable
    fun formatTime(millis: Long, gameType: GameType): String {
        val milliseconds = if (gameType == GameType.SURVIVAL) 500L - millis else millis % 1000
        return String.format("%04d", milliseconds)
    }

    @Composable
    fun ScoreBar(GameViewModel: MainViewModel){
        val data = GameViewModel.gameState.collectAsState()
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween){
            Text(text = "Score: ${data.value.score}", fontSize = 20.sp)
            Text(text = "High Score: ${ if(data.value.highScore != 0L) data.value.highScore else "N/A"}", fontSize = 20.sp)
            Text(text = "AVG: ${"%.1f".format(data.value.averageScore)}", fontSize = 20.sp)
        }
    }

    @Composable
    fun StopwatchScreen(GameViewModel: MainViewModel, gameType: GameType) {
        val coroutineScope = rememberCoroutineScope()
        var timerJob by remember { mutableStateOf<Job?>(null) }
        val data = GameViewModel.gameState.collectAsState()

        LaunchedEffect(key1 = data.value.isRunning) {
            if (data.value.isRunning) {
                timerJob?.cancel()
                timerJob = coroutineScope.launch {
                    when (gameType) {
                        GameType.SURVIVAL -> runSurvivalGame(GameViewModel, data)
                        GameType.PRECISION -> runPrecisionGame(GameViewModel, data)
                        else -> runOtherGame(GameViewModel, data)
                    }
                }
            }
        }

        ComposeClockScreen(GameViewModel, gameType, data)
    }

    private suspend fun runSurvivalGame(GameViewModel: MainViewModel, data: State<GameState>) {
        while (data.value.isRunning && data.value.elapsedTime <= 500) {
            delay(1)
            GameViewModel.updateClock(GameType.SURVIVAL)
        }
        if(data.value.elapsedTime > 500) {
            GameViewModel.startStop(GameType.SURVIVAL)
        }
    }

    private suspend fun runPrecisionGame(GameViewModel: MainViewModel, data: State<GameState>) {
        while (data.value.isRunning) {
            delay(1)
            GameViewModel.updateClock(GameType.PRECISION)
        }
    }

    private suspend fun runOtherGame(GameViewModel: MainViewModel, data: State<GameState>) {
        while (data.value.isRunning) {
            delay(1)
            GameViewModel.updateClock(GameType.SPEED)  // Replace SPEED with other game types as needed
        }
    }

    @Composable
    fun ComposeClockScreen(GameViewModel: MainViewModel, gameType: GameType, data: State<GameState>) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(.6f)
            .padding(16.dp)) {

            Text(text = Util.formatTime(data.value.elapsedTime, gameType), fontSize = 120.sp, modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .clickable {
                    GameViewModel.startStop(gameType)
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
                    GameViewModel.reset()
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
                    GameViewModel.startStop(gameType)
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