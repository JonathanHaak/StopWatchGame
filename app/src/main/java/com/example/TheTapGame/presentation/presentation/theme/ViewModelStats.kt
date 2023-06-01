package com.example.TheTapGame.presentation.presentation.theme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.TheTapGame.State.GameState
import com.example.TheTapGame.State.GameType
import com.example.TheTapGame.data.ScoresDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class ViewModelStats(
    private val gameType: GameType,
    private val dao: ScoresDao
) : ViewModel() {
    private val _gameStates = mutableMapOf<GameType, MutableStateFlow<GameState>>()

    init {
        GameType.values().forEach { gameType ->
            _gameStates[gameType] = MutableStateFlow(
                GameState(
                    gameType = gameType
                )
            )
        }
        fetchAllScores()
    }

    private fun fetchAllScores() {
        viewModelScope.launch {
            GameType.values().forEach { gameType ->
                val highScore = dao.getHighScore(gameType.name) ?: 0L
                val averageScore = dao.getAverageScore(gameType.name) ?: 0.0
                val gamesPlayed = dao.getEntryCount(gameType.name) ?: 0

                _gameStates[gameType]?.value = _gameStates[gameType]?.value?.copy(
                    highScore = highScore,
                    averageScore = averageScore,
                    gamesPlayed = gamesPlayed
                ) ?: GameState(
                    gameType = gameType,
                    highScore = highScore,
                    averageScore = averageScore,
                    gamesPlayed = gamesPlayed
                )
            }
        }
    }


    fun getGameStateFlow(gameType: GameType): StateFlow<GameState> {
        return _gameStates[gameType] ?: MutableStateFlow(GameState(gameType = gameType))
    }
}

