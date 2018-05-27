package br.com.renanbarbieri.snakotlin.presentation.engine.model

class GameContext {

    var score: Int = 0

    var isRunning: Boolean = false

    fun updateScore(addValue: Int) {
        score += addValue
    }

}