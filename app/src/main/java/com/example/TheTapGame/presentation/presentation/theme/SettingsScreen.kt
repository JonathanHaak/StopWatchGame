package com.example.TheTapGame.presentation.presentation.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.TheTapGame.State.GameType

enum class DialogState {
    NONE, SPEED, SURVIVAL, REACT, PRECISION
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavController, viewModel: ViewModelSettings) {
    var dialogState by remember { mutableStateOf(DialogState.NONE) }
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "This is the Settings Screen!",
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    fontSize = 30.sp
                )

                // Add color picker here
                ColorPicker(
                    colorScheme = viewModel.colorScheme,
                    onColorSelected = viewModel::updateColorScheme
                )

                // Button to delete scores
                for (gameType in GameType.values()) {
                    Button(onClick = { dialogState = DialogState.valueOf(gameType.name) },
                    modifier = Modifier.fillMaxWidth(.75f).height(80.dp)) {
                        Text("Clear ${gameType.name} Scores")
                    }
                }

                Button(onClick = { navController.popBackStack() }) {
                    Text("Back to Start")
                }

                // Confirmation dialogs
                if (dialogState != DialogState.NONE) {
                    AlertDialog(
                        onDismissRequest = { dialogState = DialogState.NONE },
                        title = { Text("Confirm") },
                        text = { Text("Do you really want to delete ${dialogState.name} scores?") },
                        confirmButton = {
                            Button(onClick = {
                                viewModel.clearScores(GameType.valueOf(dialogState.name))
                                dialogState = DialogState.NONE
                            }) {
                                Text("Yes")
                            }
                        },
                        dismissButton = {
                            Button(onClick = { dialogState = DialogState.NONE }) {
                                Text("No")
                            }
                        }
                    )
                }
            }
        }

        // Display snackbar message when updated

}


@Composable
fun ColorPicker(colorScheme: MutableState<ColorScheme>, onColorSelected: (Color, Color) -> Unit) {
    val colors = listOf(Color.Red, Color.Blue, Color.Green, Color.Yellow)
    Text(text = "Select primary and secondary colors", textAlign = TextAlign.Center, modifier = Modifier.padding(8.dp))
    Text(text = "Primary color", textAlign = TextAlign.Center, modifier = Modifier.padding(4.dp))
    ColorRow(colors, colorScheme.value.color1) { selectedPrimaryColor ->
        colorScheme.value = colorScheme.value.copy(color1 = selectedPrimaryColor)
    }
    Text(text = "Secondary color", textAlign = TextAlign.Center, modifier = Modifier.padding(4.dp))
    ColorRow(colors, colorScheme.value.color2) { selectedSecondaryColor ->
        colorScheme.value = colorScheme.value.copy(color2 = selectedSecondaryColor)
    }
    // update both colors on any color selection
    onColorSelected(colorScheme.value.color1, colorScheme.value.color2)
}

@Composable
fun ColorRow(colors: List<Color>, selectedColor: Color, onColorSelected: (Color) -> Unit) {
    LazyRow(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
        items(colors.size) { color ->
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .padding(4.dp)
                    .background(colors[color])
                    .clickable { onColorSelected(colors[color]) },
                contentAlignment = Alignment.Center
            ) {
                if (selectedColor == colors[color]) {
                    Text(text = "✓", color = Color.White, fontSize = 20.sp)
                }
            }
        }
    }
}


