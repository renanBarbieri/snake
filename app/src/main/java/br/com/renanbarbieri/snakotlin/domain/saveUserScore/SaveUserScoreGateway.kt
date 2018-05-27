package br.com.renanbarbieri.snakotlin.domain.saveUserScore

/**
 * Describes the necessary service for SaveUserScore domain
 */
interface SaveUserScoreGateway {
    /**
     * Function for save score
     */
    fun saveScore(value: Int)
}