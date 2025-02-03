package com.haak.theTapGame.State

data class GameState(
    val isRunning: Boolean = false,
    val elapsedTime: Long = 0L,
    val timeSinceStart: Long = 0L,
    val score: Long = 0L,
    val averageScore: Double = 0.0,
    val highScore: Long = 0L,
    val gamesPlayed: Int = 0,
    val colorIndex : Int = 0,
    val clicked : Boolean = false,
    val isGameRunning: Boolean = false,
    val isGameFinished: Boolean = false,
    val isGameStarted: Boolean = false,
    val gameType: GameType,
    val earlyStopThreshold: Long = 100L,
    val targetTime: Long = 0L,
    val timeOnClock: Long = 500L,
    val colorDelay : Long = 1000L,
    val round: Int = 1,
    val killSwitch: Boolean = false,
)