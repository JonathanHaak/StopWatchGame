package com.example.TheTapGame.presentation.presentation.theme

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.TheTapGame.State.GameState
import com.example.TheTapGame.State.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatsScreen(navController: NavController, gameViewModel: ViewModelStats) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Game Stats", fontSize = 24.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(16.dp))

        GameType.values().forEach { gameType ->
            val stats by gameViewModel.getGameStateFlow(gameType).collectAsState(null)

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "${gameType.name} Stats", fontWeight = FontWeight.Bold,
                    fontSize = 50.sp)

                    stats?.let {
                        Text(text = "High Score: ${it.highScore}", fontSize = 30.sp)
                        Text(text = "Average Score: ${"%.1f".format(it.averageScore)}",
                        fontSize = 30.sp)
                        Text(text = "Total Games Played: ${it.gamesPlayed}",
                        fontSize = 30.sp)
                    }
                }
            }
        }

        Button(onClick = { navController.popBackStack() },
        modifier = Modifier.fillMaxWidth(.6f).align(Alignment.CenterHorizontally)) {
            Text("Back to Start", fontSize = 50.sp)
        }
    }
}



