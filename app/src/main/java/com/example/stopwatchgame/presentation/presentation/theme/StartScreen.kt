package com.example.stopwatchgame.presentation.presentation.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun StartScreen(navController: NavController) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(0.dp)  // give some spacing between items
    ) {
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
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(150.dp) // increase the height here
        .clickable(onClick = onClick)
        .background(color), // add background to the box
        contentAlignment = Alignment.Center // center the text within the box
    ) {
        Text(
            text = text,
            textAlign = TextAlign.Center,
            style = TextStyle(fontSize = 30.sp, fontWeight = FontWeight.Bold), // increase the font size here
            color = Color.White // set a readable color against any background
        )
    }
}

