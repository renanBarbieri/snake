package br.com.renanbarbieri.snakotlin.engine.model

class GameContext {

    var score: Int = 0

    var isRunning: Boolean = false

    fun updateScore(addValue: Int) {
        score += addValue
    }

}