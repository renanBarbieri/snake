package br.com.renanbarbieri.snakotlin.domain.getUserTopScore

import android.arch.lifecycle.LiveData
import br.com.renanbarbieri.snakotlin.domain.entity.Score

/**
 * Describes the GetUserTopScore input domain
 */
interface GetUserTopScoreBoundary {

    /**
     * Function to get the top score
     */
    fun getTopScore(): LiveData<Score>
}