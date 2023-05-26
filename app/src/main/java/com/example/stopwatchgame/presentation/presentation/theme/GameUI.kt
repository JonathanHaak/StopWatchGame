package com.example.stopwatchgame.presentation.presentation.theme

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun GameOneScreen(GameViewModel: MainViewModel, NavController: NavController){
    Surface(modifier = Modifier.fillMaxSize()){
        Column(modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally){
            ScoreBar(GameViewModel = GameViewModel)
            StopwatchScreen(GameViewModel = GameViewModel)
            Button(onClick = { NavController.navigate("Start") {
                popUpTo("Start") { inclusive = true }
                launchSingleTop = true
            } }) {
                Text("Back to Start")
            }
        }
    }
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
    }
}
@Composable
fun StopwatchScreen(GameViewModel: MainViewModel) {
    val coroutineScope = rememberCoroutineScope()
    var timerJob by remember { mutableStateOf<Job?>(null) }
    val data = GameViewModel.gameState.collectAsState()
    LaunchedEffect(key1 = data.value.isRunning) {
        if (data.value.isRunning) {
            timerJob?.cancel()
            timerJob = coroutineScope.launch {
                while (data.value.isRunning) {
                    delay(1)
                    GameViewModel.updateClock()
                }
            }
        }
    }


    Column(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(.6f)
        .padding(16.dp)){
        Text(text = formatTime(data.value.elapsedTime), fontSize = 120.sp, modifier = Modifier
            .align(Alignment.CenterHorizontally)
            .clickable {
                GameViewModel.startStop()
            })
        Row(modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.CenterHorizontally),
            horizontalArrangement = Arrangement.Center,) {
            Button(
                onClick = {
                    GameViewModel.reset()
                    timerJob?.cancel()
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
                    GameViewModel.startStop()
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
                    if (data.value.isRunning) "Pause" else "Start",
                    fontSize = 20.sp
                )
            }
        }
    }
}

@Composable
fun formatTime(millis: Long): String {
    val milliseconds = millis % 1000
    return String.format("%04d", milliseconds)
}
