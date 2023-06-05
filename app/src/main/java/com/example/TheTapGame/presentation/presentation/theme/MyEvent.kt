package com.example.TheTapGame.presentation.presentation.theme

import com.example.TheTapGame.State.GameType

sealed class MyEvent {
    data class GameStart(val gameType: GameType) : MyEvent()
    data class GameStop(val gameType: GameType) : MyEvent()
    data class GameReset(val gameType: GameType) : MyEvent()

}
