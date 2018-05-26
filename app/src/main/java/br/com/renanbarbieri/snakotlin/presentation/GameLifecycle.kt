package br.com.renanbarbieri.snakotlin.presentation

interface GameLifecycle {
    fun onSnakeDead(score: Int)
    fun onError(errorMessage: String)
}