package br.com.renanbarbieri.snakotlin

fun random(from: Int, to: Int) = (Math.random() * (to - from) + from).toInt()