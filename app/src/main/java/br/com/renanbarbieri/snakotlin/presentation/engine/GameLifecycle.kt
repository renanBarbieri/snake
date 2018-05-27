package br.com.renanbarbieri.snakotlin.presentation.engine

/**
 * Interface the describes the game lifecycle
 */
interface GameLifecycle {
    /**
     * This function is called when the snake dies
     * @param score current score
     */
    fun onSnakeDead(score: Int)

    /**
     * This function is called when some error happens
     */
    fun onError(errorMessage: String)
}