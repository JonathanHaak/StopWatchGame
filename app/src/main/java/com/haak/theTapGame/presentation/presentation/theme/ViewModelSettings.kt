package com.haak.theTapGame.presentation.presentation.theme

import android.content.SharedPreferences
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haak.theTapGame.State.GameType
import com.haak.theTapGame.data.ScoresDao
import kotlinx.coroutines.launch

class ViewModelSettings(
    private val scoresDao: ScoresDao,
    private val preferences: SharedPreferences
) : ViewModel() {

    var colorScheme = mutableStateOf(getColorSchemeFromPreferences())

    private fun getColorSchemeFromPreferences(): ColorScheme {
        // Get ARGB color values from shared preferences with a default value corresponding to the middle of the sliders
        val color1 = preferences.getInt("color1", Color(127, 127, 127).toArgb())
        val color2 = preferences.getInt("color2", Color(127, 127, 127).toArgb())

        return ColorScheme(Color(color1), Color(color2))
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

    var darkTheme = mutableStateOf(preferences.getBoolean("darkTheme", false))

    fun updateDarkTheme(isDark: Boolean) {
        viewModelScope.launch {
            darkTheme.value = isDark
            with(preferences.edit()) {
                putBoolean("darkTheme", isDark)
                apply()
            }
        }
    }

    fun clearScores(gameType: GameType) {
        viewModelScope.launch {
            val scores = scoresDao.getScores(gameType.name)
            scores.forEach { scoresDao.deleteScore(it) }
        }
    }
}

