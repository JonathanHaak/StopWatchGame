package com.haak.theTapGame.presentation.presentation.theme

import com.haak.theTapGame.State.GameType

sealed class MyEvent {
    data class GameStart(val gameType: GameType) : MyEvent()
    data class GameStop(val gameType: GameType) : MyEvent()
    data class GameReset(val gameType: GameType) : MyEvent()

}
