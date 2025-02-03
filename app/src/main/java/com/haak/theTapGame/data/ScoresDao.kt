package com.haak.theTapGame.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert


@Dao
interface ScoresDao {

    @Upsert
    suspend fun upsertScore(score: Score)

    @Delete
    suspend fun deleteScore(score: Score)

    @Query("SELECT COALESCE(MAX(score), 0) FROM Score WHERE game = :gameType")
    suspend fun getHighScore(gameType: String): Long

    @Query("SELECT COALESCE(AVG(score), 0) FROM Score WHERE game = :gameType")
    suspend fun getAverageScore(gameType: String): Double

    @Query("SELECT COUNT(*) FROM Score WHERE game = :gameType")
    suspend fun getEntryCount(gameType: String): Int

    @Query("SELECT * FROM Score WHERE game = :gameType")
    suspend fun getScores(gameType: String): List<Score>
}