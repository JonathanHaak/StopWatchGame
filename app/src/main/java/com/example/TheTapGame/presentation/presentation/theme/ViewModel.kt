package com.example.TheTapGame.presentation.presentation.theme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.TheTapGame.State.GameState
import com.example.TheTapGame.State.GameType
import com.example.TheTapGame.data.Score
import com.example.TheTapGame.data.ScoresDao
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.lang.Long.max
import kotlin.math.absoluteValue

class MainViewModel(
    gameType: GameType,
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
        if(gameType != GameType.SURVIVAL) {
            _gameState.update {
                it.copy(timeOnClock = 0L)
            }
        }
        fetchScores()
        onEvent(MyEvent.GameReset(gameType))
    }

    private fun fetchScores() {
        viewModelScope.launch {
            val averageScore = dao.getAverageScore(gameState.value.gameType.name)
            val highScore = dao.getHighScore(gameState.value.gameType.name)
            val gamesPlayed = dao.getHighScore(gameState.value.gameType.name)
            _gameState.update {
                it.copy(averageScore = averageScore, highScore = highScore, gamesPlayed = gamesPlayed.toInt())
            }
        }
    }

    fun onEvent(event: MyEvent){
        when (event) {
            is MyEvent.GameStart -> {
                if (gameState.value.isRunning) {
                    _gameState.update { it.copy(isRunning = false) }
                    onEvent(MyEvent.GameStop(event.gameType))
                    return
                }
                when (event.gameType) {
                    GameType.SPEED -> {
                        _gameState.update {
                            it.copy(
                                score = 0L,
                                timeSinceStart = 0L,
                                elapsedTime = 0L,
                                isRunning = true
                            )
                        }
                        viewModelScope.launch {
                            runClock()
                            _gameState.update {it.copy(isRunning = false)}
                        }
                    }

                    GameType.SURVIVAL -> {
                        _gameState.update {
                            it.copy(
                                timeOnClock = 500L,
                                isRunning = true
                            )
                        }
                        viewModelScope.launch {
                            runClock()
                            _gameState.update {it.copy(isRunning = false)}
                        }
                    }

                    GameType.REACT -> {
                        _gameState.update {
                            it.copy(
                                score = 0L,
                                timeSinceStart = 0L,
                                elapsedTime = 0L,
                                isRunning = true,
                                isGameFinished = false,
                                clicked = false,
                                colorIndex = (1..7).random()
                            )
                        }
                        viewModelScope.launch {
                            runClock()
                            _gameState.update {it.copy(isRunning = false)}
                        }
                    }

                    GameType.PRECISION -> {
                        _gameState.update {
                            it.copy(
                                timeSinceStart = 0L,
                                earlyStopThreshold = max(it.earlyStopThreshold, 20),
                                elapsedTime = 0L,
                                isRunning = true,
                                targetTime = (50..500).random().toLong()
                            )
                        }
                        viewModelScope.launch {
                            runClock()
                            _gameState.update {it.copy(isRunning = false)}
                        }
                    }
                }
            }
            is MyEvent.GameStop ->
                when(event.gameType) {
                    GameType.SPEED -> {
                        val score = 1000L - gameState.value.timeOnClock - 1
                        val highScore =
                            if (score > _gameState.value.highScore) score else _gameState.value.highScore
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
                    GameType.SURVIVAL -> {
                        val stopVal = _gameState.value.earlyStopThreshold
                        if(_gameState.value.timeOnClock < 0 || _gameState.value.timeOnClock > stopVal){
                            _gameState.update { it.copy(isGameFinished = true, isRunning = false)}
                            onEvent(MyEvent.GameReset(event.gameType))
                            if(_gameState.value.score > _gameState.value.highScore){
                                _gameState.update { it.copy(highScore = _gameState.value.score)}
                            }
                            viewModelScope.launch {
                                dao.upsertScore(
                                    Score(
                                        _gameState.value.score,
                                        System.currentTimeMillis(),
                                        event.gameType.name
                                    )
                                )
                                val averageScore = dao.getAverageScore(event.gameType.name)
                                _gameState.update { it.copy(averageScore = averageScore) }
                            }
                        }
                        else {
                            when {
                                _gameState.value.timeOnClock == 0L -> {
                                    _gameState.update { it.copy(score = it.score + 1000L)}
                                }
                                _gameState.value.timeOnClock < stopVal * .1 -> {
                                    _gameState.update { it.copy(score = it.score + 100L)}
                                }
                                _gameState.value.timeOnClock < stopVal * .25 -> {
                                    _gameState.update { it.copy(score = it.score + 50L)}
                                }
                                _gameState.value.timeOnClock < stopVal * .5 -> {
                                    _gameState.update { it.copy(score = it.score + 25)}
                                }
                                else -> {
                                    _gameState.update { it.copy(score = it.score + 10)}
                                }
                            }
                            _gameState.update { it.copy(timeOnClock = 500L, round = it.round + 1, earlyStopThreshold = max(it.earlyStopThreshold - 5, 20))}
                        }
                    }
                    GameType.REACT -> {
                        if(_gameState.value.clicked){
                            return
                        }
                        when(_gameState.value.colorIndex){
                            0 -> {
                                _gameState.update { it.copy(score = it.score + 20L, clicked = true)}
                            }
                            2 -> {
                                _gameState.update { it.copy(score = it.score + 10L, clicked = true, killSwitch = false)}
                            }
                            else -> {
                                _gameState.update { it.copy(isGameFinished = true)}
                                if(_gameState.value.score > _gameState.value.highScore){
                                    _gameState.update { it.copy(highScore = _gameState.value.score)}
                                }
                                viewModelScope.launch {
                                    dao.upsertScore(
                                        Score(
                                            _gameState.value.score,
                                            System.currentTimeMillis(),
                                            event.gameType.name
                                        )
                                    )
                                    val averageScore = dao.getAverageScore(event.gameType.name)
                                    _gameState.update { it.copy(averageScore = averageScore) }
                                }
                                onEvent(MyEvent.GameReset(event.gameType))
                            }
                        }
                    }
                    GameType.PRECISION -> {
                        when(val timeDifference = (_gameState.value.elapsedTime - _gameState.value.targetTime).absoluteValue){
                            in 0..10 -> {
                                _gameState.update { it.copy(score = 1000L - (timeDifference * 5))}
                            }
                            in 11..20 -> {
                                _gameState.update { it.copy(score = 900L - (timeDifference * 5))}
                            }
                            in 21..30 -> {
                                _gameState.update { it.copy(score = 800L - (timeDifference * 5))}
                            }
                            in 31..40 -> {
                                _gameState.update { it.copy(score = 700L - (timeDifference * 5))}
                            }
                            in 41..50 -> {
                                _gameState.update { it.copy(score = 600L - (timeDifference * 5))}
                            }
                            in 51..60 -> {
                                _gameState.update { it.copy(score = 500L - (timeDifference * 5))}
                            }
                            in 61..70 -> {
                                _gameState.update { it.copy(score = 400L - (timeDifference * 5))}
                            }
                            else -> {
                                _gameState.update { it.copy(score = 0)}
                            }
                        }
                        if(_gameState.value.score > _gameState.value.highScore){
                            _gameState.update { it.copy(highScore = _gameState.value.score)}
                        }
                        viewModelScope.launch {
                            dao.upsertScore(
                                Score(
                                    _gameState.value.score,
                                    System.currentTimeMillis(),
                                    event.gameType.name
                                )
                            )
                            val averageScore = dao.getAverageScore(event.gameType.name)
                            _gameState.update { it.copy(averageScore = averageScore) }
                        }

                    }
                }
            is MyEvent.GameReset ->
                when(event.gameType) {
                    GameType.SPEED -> _gameState.update { it.copy(score = 0L, timeSinceStart = 0L,timeOnClock = 0L, elapsedTime = 0L, isRunning = false) }
                    GameType.SURVIVAL -> _gameState.update { it.copy(
                        score = 0L,
                        timeOnClock = 500L,
                        timeSinceStart = 0L,
                        elapsedTime = 0L,
                        isRunning = false,
                        earlyStopThreshold = 100,
                        round = 1) }
                    GameType.REACT -> _gameState.update { it.copy(score = 0L, timeSinceStart = 0L,timeOnClock = 0L, elapsedTime = 0L, isRunning = false, clicked = false, killSwitch = false) }
                    GameType.PRECISION -> _gameState.update { it.copy(score = 0L, timeSinceStart = 0L,timeOnClock = 0L, elapsedTime = 0L, isRunning = false) }
                }

        }
    }

    private suspend fun runClock(){
        when(_gameState.value.gameType){
            GameType.SPEED ->
                while(_gameState.value.isRunning){
                delay(1)
                _gameState.update { it.copy(elapsedTime = it.elapsedTime + 1, timeOnClock = it.timeOnClock + 1) }
            }
            GameType.SURVIVAL -> {
                while (_gameState.value.isRunning && _gameState.value.timeOnClock >= 0) {
                    _gameState.update { it.copy(timeOnClock = it.timeOnClock - 1) }
                    delay(1)
                }
                if(_gameState.value.timeOnClock < 0 ){
                    onEvent(MyEvent.GameStop(GameType.SURVIVAL))
                }
            }
            GameType.REACT -> {
                while(!_gameState.value.isGameFinished){
                    delay(_gameState.value.colorDelay)
                    _gameState.update { it.copy(
                        colorIndex = (0..7).random(),
                        colorDelay = kotlin.math.max(it.colorDelay.toDouble() * .95, 400.0 ).toLong(),
                    clicked = false) }
                    if(_gameState.value.colorIndex == 2){
                        _gameState.update {it.copy(killSwitch = true)}
                    }
                    delay(_gameState.value.colorDelay)
                }
            }
            GameType.PRECISION ->
                while(_gameState.value.isRunning && _gameState.value.elapsedTime - _gameState.value.targetTime < 500L){
                    delay(1)
                    _gameState.update { it.copy(elapsedTime = it.elapsedTime + 1, timeOnClock = it.timeOnClock + 1) }
                }
        }
    }
}