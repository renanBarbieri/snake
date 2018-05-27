package br.com.renanbarbieri.snakotlin.domain.saveUserScore

import android.arch.lifecycle.ViewModel
import br.com.renanbarbieri.snakotlin.data.repository.ScoreRepository

class SaveUserScoreViewModel
    constructor(private val saveUserScoreGateway: SaveUserScoreGateway? = ScoreRepository):
    ViewModel(), SaveUserScoreBoundary{

    override fun saveScore(score: Int) {
        saveUserScoreGateway?.saveScore(score)
    }
}