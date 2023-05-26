package com.example.stopwatchgame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.stopwatchgame.presentation.presentation.theme.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: MainViewModel by viewModels()
            val navController = rememberNavController()
            StopwatchGameTheme {
                // A surface container using the 'background' color from the theme
                NavHost(navController, startDestination = Screen.Start.route) {
                    composable(Screen.Start.route) {StartScreen(navController)}
                    composable(Screen.GameOne.route) { GameOneScreen(viewModel, navController) }
                    composable(Screen.GameTwo.route) {GameTwoScreen(navController)}
                    composable(Screen.GameThree.route) { GameThreeScreen(navController) }
                    composable(Screen.GameFour.route) { GameFourScreen(navController) }
                    composable(Screen.Info.route) { InfoScreen(navController) }
                }

            }
        }
    }
}

sealed class Screen(val route: String){
    object Start: Screen("Start")
    object GameOne: Screen("GameOne")
    object GameTwo: Screen("GameTwo")
    object GameThree: Screen("GameThree")
    object GameFour: Screen("GameFour")
    object Info: Screen("Info")

}

