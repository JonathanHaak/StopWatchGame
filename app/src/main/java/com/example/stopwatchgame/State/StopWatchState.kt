package com.example.stopwatchgame.State

data class GameState(
    val isRunning: Boolean = false,
    val elapsedTime: Long = 0L,
    val timeSinceStart: Long = 0L,
    val score: Long = 0L,
    val highScore: Long = 0L,
    val isGameRunning: Boolean = false,
    val isGameFinished: Boolean = false,
    val isGameStarted: Boolean = false,
    val gameType: GameType,
)