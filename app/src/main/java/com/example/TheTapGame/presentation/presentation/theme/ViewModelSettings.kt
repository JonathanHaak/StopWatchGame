package com.example.TheTapGame.presentation.presentation.theme

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.TheTapGame.State.GameType
import com.example.TheTapGame.data.ScoresDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ViewModelSettings(private val scoresDao: ScoresDao) : ViewModel() {
    var colorScheme = mutableStateOf(Color.White) // assuming initial color scheme is White

    fun updateColorScheme(newColor: Color) {
        colorScheme.value = newColor
        // Here you would want to save the newColor to persistent storage so it can be retrieved when the app restarts
    }
    var snackbarMessage = MutableStateFlow<String>("")
    fun clearScores(gameType: GameType) {
        viewModelScope.launch {
            val scores = scoresDao.getScores(gameType.name)
            scores.forEach { scoresDao.deleteScore(it) }
            snackbarMessage.emit("Scores for ${gameType.name} have been deleted!")
            snackbarMessage.emit("") // clear the message
        }
    }
}
