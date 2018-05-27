package br.com.renanbarbieri.snakotlin.domain.saveUserScore

/**
 * Describes the SaveUserScore input domain
 */
interface SaveUserScoreBoundary {
    /**
     * Function for save score
     */
    fun saveScore(score: Int)

}