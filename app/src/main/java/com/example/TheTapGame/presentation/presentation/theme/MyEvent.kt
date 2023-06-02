package com.example.TheTapGame.presentation.presentation.theme

import com.example.TheTapGame.State.GameType

sealed class MyEvent {
    data class gameStart(val gameType: GameType) : MyEvent()
    data class gameStop(val gameType: GameType) : MyEvent()
    data class gameReset(val gameType: GameType) : MyEvent()
    data class update(val gameType: GameType) : MyEvent()

}
