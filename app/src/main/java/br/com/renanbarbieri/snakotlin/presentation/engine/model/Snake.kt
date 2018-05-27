package br.com.renanbarbieri.snakotlin.presentation.engine.model

class Snake {
    var length: Int = 1

    fun growUp(addSize: Int) {
        this.length += addSize
    }
}