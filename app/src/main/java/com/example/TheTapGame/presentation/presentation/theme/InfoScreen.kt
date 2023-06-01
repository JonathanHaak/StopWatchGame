package com.example.TheTapGame.presentation.presentation.theme

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun InfoScreen(navController: NavController) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {

            Text(text = "This is the Info Screen!", modifier = Modifier.align(Alignment.CenterHorizontally))

            Spacer(Modifier.height(16.dp))

            Text(text = "Speed: The game where the user clicks start and then stop, getting scored based on how long it took in between their press of start and stop.")

            Spacer(Modifier.height(16.dp))

            Text(text = "Survival: A game where the time starts at a certain number and counts down. The closer the user can press to 0 without going below, the higher they score. If the clock hits 0, they lose.")

            Spacer(Modifier.height(16.dp))

            Text(text = "React: The user needs to react to color changes, pressing on the color if it is yellow for bonus points, losing points if the box is not yellow, and losing if they press too slowly on the red color.")

            Spacer(Modifier.height(16.dp))

            Text(text = "Precision: The time will start at 0 and count up towards a random number. The closer the user can stop the time to the number, the higher they score.")

            Spacer(Modifier.height(24.dp))

            Button(onClick = { navController.popBackStack() }) {
                Text("Back to Start")
            }
        }
    }
}
