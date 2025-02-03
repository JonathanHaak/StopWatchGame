package com.haak.theTapGame.presentation.presentation.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.haak.theTapGame.State.GameType


enum class DialogState {
    NONE, SPEED, SURVIVAL, REACT, PRECISION
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavController, viewModel: ViewModelSettings) {
    var dialogState by remember { mutableStateOf(DialogState.NONE) }
    var colorScheme by remember { viewModel.colorScheme }
    Surface(modifier = Modifier.fillMaxSize(),
    color = colorScheme.color1) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Text(
                    text = "This is the Settings Screen!",
                    fontSize = 30.sp
                )
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(text = "Night Mode", style = MaterialTheme.typography.bodyLarge)
                    Spacer(modifier = Modifier.width(16.dp))

                    Switch(
                        checked = viewModel.darkTheme.value,
                        onCheckedChange = viewModel::updateDarkTheme
                    )
                }
            }


            item {
                // Add color picker here
                ColorPicker(
                    colorScheme = viewModel.colorScheme,
                    onColorSelected = viewModel::updateColorScheme
                )
            }
            item {
                ColorPicker2(
                    colorScheme = viewModel.colorScheme,
                    onColorSelected = viewModel::updateColorScheme
                )
            }

            items(GameType.values().size) { gameType ->
                // Button to delete scores
                Button(onClick = { dialogState = DialogState.valueOf(GameType.values()[gameType].name)},
                    modifier = Modifier
                        .fillMaxWidth(.75f)
                        .height(80.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = colorScheme.color2,
                        contentColor = colorScheme.color1)) {
                    Text("Clear ${GameType.values()[gameType].name} Scores")
                }
            }

            item {
                Button(onClick = { navController.popBackStack() }) {
                    Text("Back to Start")
                }
            }
        }

        // Move AlertDialog outside of LazyColumn
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



@Composable
fun ColorPicker(colorScheme: MutableState<ColorScheme>, onColorSelected: (Color, Color) -> Unit) {
    val red = remember { mutableStateOf((colorScheme.value.color1.red * 255) ?: 200f) }
    val green = remember { mutableStateOf(colorScheme.value.color1.green * 255 ?: 200f) }
    val blue = remember { mutableStateOf(colorScheme.value.color1.blue * 255 ?: 200f) }

    Column (
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Primary Color", modifier = Modifier.padding(8.dp))
        ColorSlider(value = red.value, onValueChange = { red.value = it }, color = Color.Red)
        ColorSlider(value = green.value, onValueChange = { green.value = it }, color = Color.Green)
        ColorSlider(value = blue.value, onValueChange = { blue.value = it }, color = Color.Blue)

        val primaryColor = Color(red.value.toInt(), green.value.toInt(), blue.value.toInt())
        Box(modifier = Modifier
            .size(250.dp)
            .background(color = primaryColor)
            .border(1.dp, Color.Black)
            .padding(8.dp))

        Button(onClick = {
            onColorSelected(primaryColor, colorScheme.value.color2)
        }) {
            Text("Set Primary Color")
        }
    }
}

@Composable
fun ColorPicker2(colorScheme: MutableState<ColorScheme>, onColorSelected: (Color, Color) -> Unit) {
    val red = remember { mutableStateOf(colorScheme.value.color2.red * 255 ?: 100f) }
    val green = remember { mutableStateOf(colorScheme.value.color2.green * 255 ?: 100f) }
    val blue = remember { mutableStateOf(colorScheme.value.color2.blue * 255 ?: 100f) }

    Column (
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Primary Color", modifier = Modifier.padding(8.dp))
        ColorSlider(value = red.value, onValueChange = { red.value = it }, color = Color.Red)
        ColorSlider(value = green.value, onValueChange = { green.value = it }, color = Color.Green)
        ColorSlider(value = blue.value, onValueChange = { blue.value = it }, color = Color.Blue)

        val secondaryColor = Color(red.value.toInt(), green.value.toInt(), blue.value.toInt())
        Box(modifier = Modifier
            .size(250.dp)
            .background(color = secondaryColor)
            .border(1.dp, Color.Black)
            .padding(8.dp))

        Button(onClick = {
            onColorSelected(colorScheme.value.color1, secondaryColor)
        }) {
            Text("Set Secondary Color")
        }
    }
}


@Composable
fun ColorSlider(value: Float, onValueChange: (Float) -> Unit, color: Color) {
    Slider(value = value, valueRange = 0f..255f, onValueChange = onValueChange,
        colors = SliderDefaults.colors(
            thumbColor = color,
            activeTrackColor = color,
            inactiveTrackColor = color.copy(alpha = 0.4f)
        ),
        modifier = Modifier.fillMaxWidth(.9f)
    )
}


