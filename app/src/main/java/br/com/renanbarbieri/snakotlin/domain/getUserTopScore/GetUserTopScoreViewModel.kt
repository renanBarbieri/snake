package br.com.renanbarbieri.snakotlin.domain.getUserTopScore

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import br.com.renanbarbieri.snakotlin.data.repository.ScoreRepository
import br.com.renanbarbieri.snakotlin.domain.entity.Score

class GetUserTopScoreViewModel
    constructor(private val getUserScoreGateway: GetUserTopScoreGateway = ScoreRepository):
    ViewModel(), GetUserTopScoreBoundary {

    var topScoreLiveData: MutableLiveData<Score> = MutableLiveData()

    override fun getTopScore(): LiveData<Score> {
        try {
            val score: Score = getUserScoreGateway.getTopScore()
            this.topScoreLiveData.value = score
        } catch (e: Exception) {
            this.topScoreLiveData.value = null
        }

        return topScoreLiveData
    }
}