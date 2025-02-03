package com.haak.theTapGame.presentation.presentation.theme

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun InfoScreen(navController: NavController) {
    val commonTextStyle = TextStyle(fontSize = 30.sp)

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Text(text = "This is the Info Screen!", modifier = Modifier.align(Alignment.CenterHorizontally), style = commonTextStyle)

            Spacer(Modifier.height(16.dp))

            Text(text = "Speed: Click start and then Stop as fast as you can!", style = commonTextStyle)

            Spacer(Modifier.height(16.dp))

            Text(text = "Survival: Make sure the time gets below the death time, but don't let it hit 0!", style = commonTextStyle)

            Spacer(Modifier.height(16.dp))

            Text(text = "React: Tap on the Yellow box for points, on the Red box to survive, but don't tap anywhere else!", style = commonTextStyle)

            Spacer(Modifier.height(16.dp))

            Text(text = "Precision: Get as close to the target number as possible!", style = commonTextStyle)

            Spacer(Modifier.height(24.dp))

            Button(onClick = { navController.popBackStack() }) {
                Text("Back to Start")
            }
        }
    }
}

