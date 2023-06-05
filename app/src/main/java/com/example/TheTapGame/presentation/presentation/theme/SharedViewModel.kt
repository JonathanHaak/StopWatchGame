package com.example.TheTapGame.presentation.presentation.theme

import android.content.SharedPreferences
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel

class SharedViewModel(private val preferences: SharedPreferences) : ViewModel() {
    var colorScheme = mutableStateOf(getColorSchemeFromPreferences())

    private fun getColorSchemeFromPreferences(): ColorScheme {
        // Retrieve the colors from the shared preferences and return a ColorScheme.
        val color1 = Color(preferences.getInt("color1", Color.White.toArgb()))
        val color2 = Color(preferences.getInt("color2", Color.White.toArgb()))
        return ColorScheme(color1, color2)
    }
}
