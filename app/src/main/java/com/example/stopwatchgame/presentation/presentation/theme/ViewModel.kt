package com.example.stopwatchgame.presentation.presentation.theme

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.stopwatchgame.State.GameState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MainViewModel : ViewModel(

) {
    private val _gameState = MutableStateFlow(GameState(false, 0L))
    val gameState: StateFlow<GameState> = _gameState.asStateFlow()
    fun startStop() {
        _gameState.update { it.copy(isRunning = !it.isRunning) }
    }

    fun reset() {
        _gameState.update { it.copy(isRunning = false, elapsedTime = 0L) }
    }

    fun updateClock() {
        _gameState.update { it.copy(elapsedTime = it.elapsedTime + 1) }
    }

}