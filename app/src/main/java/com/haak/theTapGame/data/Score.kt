package com.haak.theTapGame.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Score(
    val score: Long,
    val date: Long,
    val game: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)
