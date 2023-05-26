package com.example.stopwatchgame.presentation.presentation.theme

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stopwatchgame.State.GameState
import com.example.stopwatchgame.State.GameType
import com.example.stopwatchgame.data.Score
import com.example.stopwatchgame.data.ScoresDao
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val dao: ScoresDao
) : ViewModel() {
    private val _gameState = MutableStateFlow(
        GameState(false, 0L, 0L,0L, 0L, false, false, false, GameType.FASTCLICK))
    val gameState: StateFlow<GameState> = _gameState.asStateFlow()
    fun startStop() {
            viewModelScope.launch {
                _gameState.update {

                        it.copy(

                            score = if(it.isRunning) calculateScore(it.timeSinceStart) else it.score,
                            timeSinceStart = 0L,
                            highScore = if (it.score > it.highScore) it.score else it.highScore,
                            isRunning = !it.isRunning,
                        )


                    }
                dao.upsertScore(Score(gameState.value.score, System.currentTimeMillis(), gameState.value.gameType.name))
            }
    }

    fun reset() {
        _gameState.update { it.copy(isRunning = false, elapsedTime = 0L, timeSinceStart = 0L) }
    }

    fun updateClock() {
        _gameState.update {
            it.copy(elapsedTime = it.elapsedTime + 1, timeSinceStart = it.timeSinceStart + 1)}
    }

}

fun calculateScore(time: Long): Long{
    val score: Long = 1000L - time
    if(score > 0L){
        return score
    }
    return 0
}