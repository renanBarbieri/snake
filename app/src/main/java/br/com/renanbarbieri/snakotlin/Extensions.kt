package br.com.renanbarbieri.snakotlin

fun random(from: Int, to: Int): Int = (Math.random() * (to - from) + from).toInt()

inline fun <T> T.guard(block: T.() -> Unit): T {
    if (this == null) block(); return this
}