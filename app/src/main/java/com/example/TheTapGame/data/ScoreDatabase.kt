package com.example.TheTapGame.data

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(
    entities = [Score::class],
    version = 1)
abstract class ScoreDatabase : RoomDatabase(){
    abstract val dao: ScoresDao
}