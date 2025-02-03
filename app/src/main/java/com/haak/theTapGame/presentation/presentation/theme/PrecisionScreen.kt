package com.haak.theTapGame.presentation.presentation.theme

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.haak.theTapGame.State.GameType

@Composable
fun PrecisionScreen(NavController: NavController, GameViewModel: MainViewModel){
    val gameState = GameViewModel.gameState.collectAsState().value
    Surface(modifier = Modifier.fillMaxSize()){
        Column(modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = CenterHorizontally
        ){

            // Empty space
            Spacer(modifier = Modifier.weight(1f))

            if(gameState.isGameFinished){
                Text(text = "Number was ${gameState.targetTime}, your stop time was ${gameState.elapsedTime}, " +
                        "your score is ${gameState.score}",
                    fontSize = 30.sp,
                    modifier = Modifier.align(CenterHorizontally))
            }
            else {
                Text(text = "Get as close to the target number as you can!",
                    fontSize = 30.sp,
                    modifier = Modifier.align(CenterHorizontally))
            }
            Text(text = "Precision", modifier = Modifier
                .align(CenterHorizontally),
                fontSize = 30.sp)
            Util.ScoreBar(GameViewModel = GameViewModel)
            Util.StopwatchScreen(GameViewModel = GameViewModel, gameType = GameType.PRECISION)
            Button(onClick = { NavController.popBackStack() }) {
                Text("Back to Start")
            }

            // Empty space
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

