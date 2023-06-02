package com.example.TheTapGame.presentation.presentation.theme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.TheTapGame.State.GameState
import com.example.TheTapGame.State.GameType
import com.example.TheTapGame.State.Stats
import com.example.TheTapGame.data.Score
import com.example.TheTapGame.data.ScoresDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.abs

class MainViewModel(
    private val gameType: GameType,
    private val dao: ScoresDao
) : ViewModel() {
    private val _gameState = MutableStateFlow(
        GameState(
            gameType = gameType,
            elapsedTime = 0L
        )
    )
    val gameState: StateFlow<GameState> = _gameState.asStateFlow()

    init {
        fetchScores()
    }

    private fun fetchScores() {
        viewModelScope.launch {
            val averageScore = if(dao.getAverageScore(gameState.value.gameType.name) == null) 0.0 else dao.getAverageScore(gameState.value.gameType.name)!!
            val highScore = if(dao.getHighScore(gameState.value.gameType.name) == null) 0L else dao.getHighScore(gameState.value.gameType.name)!!
            val gamesPlayed = if(dao.getEntryCount(gameState.value.gameType.name) == null) 0 else dao.getHighScore(gameState.value.gameType.name)!!
            _gameState.update {
                it.copy(averageScore = averageScore, highScore = highScore, gamesPlayed = gamesPlayed.toInt())
            }
        }
    }
    fun reset() {
        val randomTargetTime = (50..500).random().toLong()
        _gameState.update {
            it.copy(isRunning = false, elapsedTime = 0L, timeSinceStart = 0L, targetTime = randomTargetTime)
        }
    }

    fun startStop(gameType: GameType) {
        viewModelScope.launch(Dispatchers.IO) {
            var highScore = 0L
            var averageScore = 0.0
            var score = 0L

            if (gameState.value.isRunning) {
                highScore = dao.getHighScore(gameState.value.gameType.name) ?: 0L
                averageScore = dao.getAverageScore(gameState.value.gameType.name) ?: 0.0
                score = when (gameType) {
                    GameType.SPEED -> calculateScore(gameState.value.timeSinceStart, gameType)
                    GameType.SURVIVAL -> {
                        if (gameState.value.elapsedTime <= 0 || gameState.value.elapsedTime > gameState.value.earlyStopThreshold) {
                            -1L  // Player loses
                        } else {
                            // Player survives
                            if (gameState.value.elapsedTime == 0L) {
                                10L  // Bonus score
                            } else {
                                5L
                            }
                        }
                    }
                    GameType.REACT -> gameState.value.score
                    GameType.PRECISION -> {
                        val diff = abs(gameState.value.targetTime - gameState.value.elapsedTime)
                        if(diff <= 5) {
                            500L
                        } else if (diff <= 10) {
                            400L
                        } else if (diff <= 20) {
                            300L
                        } else if (diff <= 50) {
                            200L
                        } else {
                            100L
                        }
                    }// Score is updated in updateClock, so just retrieve the current score
                    else -> {
                        0L
                    }
                }
            } else if (gameType == GameType.REACT) {
                // Game is not running and is now started for REACT game
                viewModelScope.launch(Dispatchers.Default) {
                    var delay = 1000L  // Initial delay
                    while (true) {
                        delay(delay)
                        withContext(Dispatchers.Main) {
                            _gameState.update {
                                it.copy(
                                    colorIndex = (it.colorIndex + 1) % 3,
                                )
                            }
                        }

                        // Decrease delay to speed up color change
                        delay -= 100
                        if (delay < 100) delay = 100  // Limit minimum delay
                    }
                }
            } else {
                // Game is not running and is now started for other games
                // No score computation here
                score = gameState.value.score
            }

            dao.upsertScore(Score(score, System.currentTimeMillis(), gameState.value.gameType.name))

            withContext(Dispatchers.Main) {
                _gameState.update {
                    it.copy(
                        score = score,
                        timeSinceStart = 0L,
                        highScore = highScore,
                        averageScore = averageScore,
                        isRunning = !it.isRunning,
                    )
                }
            }
        }
    }

    fun updateClock(gameType: GameType) {
        _gameState.update { state ->
            when (gameType) {
                GameType.SURVIVAL -> {
                    state.copy(elapsedTime = state.elapsedTime + 1, timeSinceStart = state.timeSinceStart + 1)
                }
                GameType.SPEED -> {
                    state.copy(elapsedTime = state.elapsedTime + 1, timeSinceStart = state.timeSinceStart + 1)
                }
                GameType.REACT -> {
                    // scoring logic goes here, for example:
                    // if colorIndex is yellow and user has clicked, increment the score by 10
                    // if colorIndex is not yellow and user has clicked, decrement the score by 5
                    // if colorIndex is red and the box is not clicked within the allowed time, the game ends
                    // you need to adjust these rules based on your specific requirements
                    if (state.clicked) {
                        val newScore = when (state.colorIndex) {
                            0 -> state.score + 10  // Yellow
                            else -> state.score - 5  // Other colors
                        }
                        state.copy(score = newScore, clicked = false)  // Reset clicked state
                    } else if (state.colorIndex == 2 && state.elapsedTime > state.earlyStopThreshold) {  // Red
                        state.copy(isRunning = false)  // End the game
                    } else {
                        state.copy(elapsedTime = state.elapsedTime + 1, timeSinceStart = state.timeSinceStart + 1)
                    }
                }
                GameType.PRECISION -> {
                    state.copy(elapsedTime = state.elapsedTime + 1, timeSinceStart = state.timeSinceStart + 1)
                }
            }
        }
    }



    private fun calculateScore(time: Long, gameType: GameType): Long {
        return when(gameType) {
            GameType.SPEED -> {
                val score: Long = 1000L - time
                if(score > 0L){
                    score
                } else {
                    0L
                }
            }
            GameType.SURVIVAL -> {
                if (time > 0) {
                    5L
                } else if (time == 0L) {
                    10L
                } else {
                    0L
                }
            }
            else -> {
                0L}
        }
    }

    fun onEvent(event: MyEvent){
        when (event) {
            is MyEvent.gameStart ->
                when (event.gameType) {
                    GameType.SPEED -> _gameState.update { it.copy(score = 0L, timeSinceStart = 0L, elapsedTime = 0L, isRunning = true)}
                    GameType.SURVIVAL -> _gameState.update { it.copy(score = 0L, timeSinceStart = 0L, elapsedTime = 0L, isRunning = true)}
                    GameType.REACT -> _gameState.update { it.copy(score = 0L, timeSinceStart = 0L, elapsedTime = 0L, isRunning = true, clicked = false, colorIndex = 0)}
                    GameType.PRECISION -> startStop(event.gameType)
                }
            is MyEvent.gameStop ->
                when(event.gameType) {
                    GameType.SPEED -> {
                        val score = 1000L - gameState.value.timeSinceStart
                        val highScore =
                            if (score > gameState.value.highScore) score else gameState.value.highScore
                        _gameState.update {
                            it.copy(
                                score = score,
                                highScore = highScore,
                                isRunning = false,
                                timeSinceStart = 0L
                            )
                        }
                        viewModelScope.launch {
                            dao.upsertScore(
                                Score(
                                    score,
                                    System.currentTimeMillis(),
                                    event.gameType.name
                                )
                            )
                            val averageScore = dao.getAverageScore(event.gameType.name)
                            _gameState.update { it.copy(averageScore = averageScore) }
                        }
                    }
                    GameType.SURVIVAL -> updateClock(event.gameType)
                    GameType.REACT -> updateClock(event.gameType)
                    GameType.PRECISION -> startStop(event.gameType)
                }
            is MyEvent.gameReset ->
                when(event.gameType) {
                    GameType.SPEED -> _gameState.update { it.copy(score = 0L, timeSinceStart = 0L, elapsedTime = 0L, isRunning = false) }
                    GameType.SURVIVAL -> _gameState.update { it.copy(score = 0L, timeSinceStart = 0L, elapsedTime = 0L, isRunning = false, earlyStopThreshold = 100) }
                    GameType.REACT -> updateClock(event.gameType)
                    GameType.PRECISION -> _gameState.update { it.copy(score = 0L, timeSinceStart = 0L, elapsedTime = 0L, isRunning = false, targetTime = (50..500).random().toLong()) }
                }
            is MyEvent.update ->
                when(event.gameType) {
                    GameType.SPEED -> _gameState.update { it.copy(elapsedTime = it.elapsedTime + 1, timeSinceStart = it.timeSinceStart + 1) }
                    GameType.SURVIVAL -> _gameState.update { it.copy(elapsedTime = it.elapsedTime + 1, timeSinceStart = it.timeSinceStart + 1) }
                    GameType.REACT -> _gameState.update { it.copy(elapsedTime = it.elapsedTime + 1, timeSinceStart = it.timeSinceStart + 1) }
                    GameType.PRECISION -> _gameState.update { it.copy(elapsedTime = it.elapsedTime + 1, timeSinceStart = it.timeSinceStart + 1) }
                }
        }
    }
}