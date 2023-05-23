package com.example.stopwatchgame.State

data class GameState(
    val isRunning: Boolean = false, val elapsedTime: Long = 0L
)