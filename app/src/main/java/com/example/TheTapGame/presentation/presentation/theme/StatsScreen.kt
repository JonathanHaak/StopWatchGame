package com.example.TheTapGame.presentation.presentation.theme

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.TheTapGame.State.GameState
import com.example.TheTapGame.State.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatsScreen(navController: NavController, gameViewModel: ViewModelStats) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)   ) {
            StatsTitle()
            Spacer(modifier = Modifier.height(16.dp))
            GameStatsList(gameViewModel = gameViewModel)
            NavigationButton(navController = navController)
        }
    }
}

@Composable
fun StatsTitle() {
    Text(text = "Game Stats", fontSize = 24.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
}

@Composable
fun GameStatsList(gameViewModel: ViewModelStats) {
    GameType.values().forEach { gameType ->
        val stats by gameViewModel.getGameStateFlow(gameType).collectAsState(null)

        Surface(modifier = Modifier.padding(bottom = 16.dp)) {
            StatsCard(gameType = gameType, stats = stats)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatsCard(gameType: GameType, stats: GameState?) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column {
            Text(text = "${gameType.name} Stats", fontWeight = FontWeight.Bold,
                fontSize = 30.sp)

            stats?.let {
                Text(text = "High Score: ${it.highScore}", fontSize = 20.sp)
                Text(text = "Average Score: ${"%.1f".format(it.averageScore)}",
                    fontSize = 20.sp)
                Text(text = "Total Games Played: ${it.gamesPlayed}",
                    fontSize = 20.sp)
            }
        }
    }
}

@Composable
fun NavigationButton(navController: NavController) {
        Button(onClick = { navController.popBackStack() }) {
            Text("Back to Start", fontSize = 20.sp)
        }
}




