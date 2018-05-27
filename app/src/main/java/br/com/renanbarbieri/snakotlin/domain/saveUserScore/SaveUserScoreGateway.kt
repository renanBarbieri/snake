package br.com.renanbarbieri.snakotlin.domain.saveUserScore

interface SaveUserScoreGateway {
    fun saveScore(value: Int)
}