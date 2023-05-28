package com.example.stopwatchgame.presentation.presentation.theme

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun StartScreen(navController: NavController) {
    LazyColumn(horizontalAlignment = Alignment.CenterHorizontally) {
        item {
            CustomText(text = "GameOne", onClick = { navController.navigate("GameOne") }, color = Color.Red)
        }
        item {
            CustomText(text = "GameTwo", onClick = { navController.navigate("GameTwo") }, color = Color.Green)
        }
        item {
            CustomText(text = "GameThree", onClick = { navController.navigate("GameThree") }, color = Color.Blue)
        }
        item {
            CustomText(text = "GameFour", onClick = { navController.navigate("GameFour") }, color = Color.Yellow)
        }
        item {
            CustomText(text = "Info", onClick = { navController.navigate("Info") }, color = Color.Magenta)
        }
    }
}

@Composable
fun CustomText(text: String, onClick: () -> Unit, color: Color) {
    Text(
        text = text,
        style = TextStyle(color = color, fontSize = 50.sp, fontWeight = FontWeight.Bold),
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    )
}
