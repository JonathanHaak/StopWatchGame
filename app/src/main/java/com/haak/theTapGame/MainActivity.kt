package com.haak.theTapGame

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.haak.theTapGame.State.GameType
import com.haak.theTapGame.data.ScoreDatabase
import com.haak.theTapGame.data.ScoresDao
import com.haak.theTapGame.presentation.presentation.theme.*

class MainActivity : ComponentActivity() {
    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            ScoreDatabase::class.java,
            "score.db"
        ).build()
    }
    private val preferences: SharedPreferences by lazy {
        getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val viewModel = viewModel<ViewModelSettings>(factory = ViewModelSettingsFactory(GameType.SPEED, db.dao, preferences))
            var colorScheme = viewModel.colorScheme.value
            StopwatchGameTheme (viewModel = viewModel, userScheme = colorScheme) {
                NavHost(navController, startDestination = Screen.Start.route) {
                    composable(Screen.Start.route) { StartScreen(navController) }
                    composable(Screen.GameOne.route) {
                        val viewModel = viewModel<MainViewModel>(factory = GameViewModelFactory(GameType.SPEED, db.dao))
                        SpeedScreen(navController, viewModel)
                    }
                    composable(Screen.GameTwo.route) {
                        val viewModel = viewModel<MainViewModel>(factory = GameViewModelFactory(GameType.SURVIVAL, db.dao))
                        SurvivalScreen(navController, viewModel)
                    }
                    composable(Screen.GameThree.route) {
                        val viewModel = viewModel<MainViewModel>(factory = GameViewModelFactory(GameType.REACT, db.dao))
                        ReactScreen(navController, viewModel)
                    }
                    composable(Screen.GameFour.route) {
                        val viewModel = viewModel<MainViewModel>(factory = GameViewModelFactory(GameType.PRECISION, db.dao))
                        PrecisionScreen(navController, viewModel)
                    }
                    composable(Screen.Stats.route) {
                        val viewModel2 = viewModel<ViewModelStats>(factory = ViewModelStatsFactory(GameType.SPEED, db.dao)) // use whichever GameType you like here
                        StatsScreen(navController, viewModel2, viewModelSettings = viewModel)
                    }
                    composable(Screen.Settings.route) {
                        SettingsScreen(navController, viewModel)
                    }
                    composable(Screen.Info.route) { InfoScreen(navController) }
                }
            }
        }
    }

    class GameViewModelFactory(private val gameType: GameType, private val dao: ScoresDao): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MainViewModel(gameType, dao) as T
        }
    }


    class ViewModelStatsFactory(private val gameType: GameType, private val dao: ScoresDao): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ViewModelStats(dao) as T
        }
    }

    class ViewModelSettingsFactory(private val gameType: GameType, private val dao: ScoresDao, private val preferences: SharedPreferences): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ViewModelSettings(dao, preferences) as T
        }
    }


}

sealed class Screen(val route: String){
    object Start: Screen("Start")
    object GameOne: Screen("GameOne")
    object GameTwo: Screen("GameTwo")
    object GameThree: Screen("GameThree")
    object GameFour: Screen("GameFour")

    object Stats: Screen("Stats")
    object Settings: Screen("Settings")
    object Info: Screen("Info")

}

