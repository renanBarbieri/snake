package br.com.renanbarbieri.snakotlin.engine.model

class Snake {
    var length: Int = 1

    fun growUp(addSize: Int) {
        this.length += addSize
    }
}