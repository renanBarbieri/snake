package br.com.renanbarbieri.snakotlin.domain.getUserTopScore

import android.arch.lifecycle.LiveData
import br.com.renanbarbieri.snakotlin.domain.entity.Score

interface GetUserTopScoreBoundary {

    fun getTopScore(): LiveData<Score>
}