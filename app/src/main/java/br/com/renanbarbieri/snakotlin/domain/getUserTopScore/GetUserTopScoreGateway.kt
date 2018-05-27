package br.com.renanbarbieri.snakotlin.domain.getUserTopScore

import br.com.renanbarbieri.snakotlin.domain.entity.Score

interface GetUserTopScoreGateway {
    fun getTopScore(): Score
}