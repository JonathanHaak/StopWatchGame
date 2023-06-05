package com.example.TheTapGame.presentation.presentation.theme

import android.content.SharedPreferences
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.TheTapGame.State.GameType
import com.example.TheTapGame.data.ScoresDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ViewModelSettings(
    private val scoresDao: ScoresDao,
    private val preferences: SharedPreferences
) : ViewModel() {

    var colorScheme = mutableStateOf(getColorSchemeFromPreferences())

    private fun getColorSchemeFromPreferences(): ColorScheme {
        // Here, you'll retrieve the colors from the shared preferences and return a ColorScheme.
        val color1 = Color(preferences.getInt("color1", Color.White.toArgb()))
        val color2 = Color(preferences.getInt("color2", Color.White.toArgb()))
        return ColorScheme(color1, color2)
    }

    fun updateColorScheme(newColor1: Color, newColor2: Color) {
        colorScheme.value = ColorScheme(newColor1, newColor2)
        // Save the color scheme to shared preferences
        with (preferences.edit()) {
            putInt("color1", newColor1.toArgb())
            putInt("color2", newColor2.toArgb())
            apply()
        }
    }

    fun clearScores(gameType: GameType) {
        viewModelScope.launch {
            val scores = scoresDao.getScores(gameType.name)
            scores.forEach { scoresDao.deleteScore(it) }
        }
    }
}

