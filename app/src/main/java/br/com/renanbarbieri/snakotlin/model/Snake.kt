package br.com.renanbarbieri.snakotlin.model

class Snake {
    var length: Int = 1

    public fun growUp() = this.length++

    public fun growUp(addSize: Int) {
        this.length += addSize
    }
}