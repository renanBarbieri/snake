package br.com.renanbarbieri.snakotlin

interface GameLifecycle {
    fun onSnakeDead(score: Int)
    fun onError(errorMessage: String)
}