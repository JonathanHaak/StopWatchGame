package com.haak.theTapGame.presentation.presentation.theme

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.haak.theTapGame.State.GameState
import com.haak.theTapGame.State.*
import com.haak.theTapGame.presentation.presentation.theme.Util.isLight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatsScreen(navController: NavController, gameViewModel: ViewModelStats, viewModelSettings: ViewModelSettings) {
    val colorScheme by remember {viewModelSettings.colorScheme}
    Surface(modifier = Modifier.fillMaxSize(),
        color = colorScheme.color1) {
        Column(horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)   ) {
            StatsTitle()
            Spacer(modifier = Modifier.height(16.dp))
            GameStatsList(gameViewModel = gameViewModel, colorScheme = colorScheme)
            NavigationButton(navController = navController, colorScheme = colorScheme)
        }
    }
}

@Composable
fun StatsTitle() {
    Text(text = "Game Stats", fontSize = 24.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
}

@Composable
fun GameStatsList(gameViewModel: ViewModelStats, colorScheme: ColorScheme) {
    GameType.values().forEach { gameType ->
        val stats by gameViewModel.getGameStateFlow(gameType).collectAsState(null)

        Surface(modifier = Modifier.padding(bottom = 16.dp)) {
            StatsCard(gameType = gameType, stats = stats, colorScheme = colorScheme)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatsCard(gameType: GameType, stats: GameState?, colorScheme: ColorScheme){
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = colorScheme.color2
        )
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
fun NavigationButton(navController: NavController, colorScheme: ColorScheme) {
    Button(
        onClick = { navController.popBackStack() },
        colors = ButtonDefaults.buttonColors(
            containerColor = colorScheme.color2 // Setting the button background to the secondary color
        )
    ) {
        Text("Back to Start", fontSize = 20.sp, color = if (isLight(colorScheme.color2)) Color.Black else Color.White)
    }
}




