package com.example.stopwatchgame.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Upsert


@Dao
interface ScoresDao {

    @Upsert
    suspend fun upsertScore(score: Score)

    @Delete
    suspend fun deleteScore(score: Score)



}