package br.com.renanbarbieri.snakotlin

import android.app.Application
import br.com.renanbarbieri.snakotlin.data.repository.ScoreRepository

class SnakeApplication: Application(){

    override fun onCreate() {
        super.onCreate()
        ScoreRepository.initRepository(this)
    }
}