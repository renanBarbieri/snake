package br.com.renanbarbieri.snakotlin.data.database.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import br.com.renanbarbieri.snakotlin.domain.entity.Score

@Dao interface ScoreDao {

    @Insert
    fun save(scoreDb: Score)

    @Query("SELECT * FROM score ORDER BY value DESC LIMIT 1")
    fun findTopScore(): Score
}