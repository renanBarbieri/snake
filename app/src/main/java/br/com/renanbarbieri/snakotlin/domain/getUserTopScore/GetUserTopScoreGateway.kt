package br.com.renanbarbieri.snakotlin.domain.getUserTopScore

import br.com.renanbarbieri.snakotlin.domain.entity.Score

/**
 * Describes the necessary service for GetUserTopScore domain
 */
interface GetUserTopScoreGateway {

    /**
     * Function to get the top score
     */
    fun getTopScore(): Score
}