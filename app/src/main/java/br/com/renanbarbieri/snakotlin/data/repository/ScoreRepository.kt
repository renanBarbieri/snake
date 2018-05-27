package br.com.renanbarbieri.snakotlin.data.repository

import android.content.Context
import br.com.renanbarbieri.snakotlin.data.database.LocalDatabase
import br.com.renanbarbieri.snakotlin.data.database.dao.ScoreDao
import br.com.renanbarbieri.snakotlin.domain.entity.Score
import br.com.renanbarbieri.snakotlin.domain.getUserTopScore.GetUserTopScoreGateway
import br.com.renanbarbieri.snakotlin.domain.saveUserScore.SaveUserScoreGateway

/**
 * Responsible for request to some service the data
 */
object ScoreRepository: GetUserTopScoreGateway, SaveUserScoreGateway {

    private var scoreDao: ScoreDao? = null

    /**
     * Init the LocalDatabase
     */
    fun initRepository(context: Context) {
        scoreDao = LocalDatabase.getInstance(context)?.scoreDao()
    }

    /**
     * Implements SaveUserScoreGateway
     */
    override fun saveScore(value: Int) {
        val score: Score = Score()
        score.value = value
        scoreDao?.let {
            it.save(score)
            return
        }
    }

    /**
     * Implements GetUserTopScoreGateway
     */
    override fun getTopScore(): Score {
        scoreDao?.let {
            return it.findTopScore()
        }

        throw Exception("Repository was not initialized")
    }
}