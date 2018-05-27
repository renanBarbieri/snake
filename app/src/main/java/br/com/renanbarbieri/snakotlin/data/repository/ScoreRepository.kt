package br.com.renanbarbieri.snakotlin.data.repository

import android.content.Context
import br.com.renanbarbieri.snakotlin.data.database.LocalDatabase
import br.com.renanbarbieri.snakotlin.data.database.dao.ScoreDao
import br.com.renanbarbieri.snakotlin.domain.entity.Score
import br.com.renanbarbieri.snakotlin.domain.getUserTopScore.GetUserTopScoreGateway
import br.com.renanbarbieri.snakotlin.domain.saveUserScore.SaveUserScoreGateway

object ScoreRepository: GetUserTopScoreGateway, SaveUserScoreGateway {

    private var scoreDao: ScoreDao? = null

    fun initRepository(context: Context) {
        scoreDao = LocalDatabase.getInstance(context)?.scoreDao()
    }

    override fun saveScore(value: Int) {
        val score: Score = Score()
        score.value = value
        scoreDao?.let {
            it.save(score)
            return
        }
    }

    override fun getTopScore(): Score {
        scoreDao?.let {
            return it.findTopScore()
        }

        throw Exception("Repository was not initialized")
    }
}