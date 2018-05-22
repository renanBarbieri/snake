package br.com.renanbarbieri.snakotlin

import android.content.Context
import android.graphics.Point
import android.view.SurfaceView
import br.com.renanbarbieri.snakotlin.model.GameContext
import br.com.renanbarbieri.snakotlin.model.GameMap

class GameView(context: Context): SurfaceView(context), Runnable {

    // Game Map instance
    private var map: GameMap? = null
    private val gameContext: GameContext = GameContext()

    constructor(context: Context, screenDimen: Point): this(context) {
        this.map = GameMap(width = screenDimen.x, height = screenDimen.y)
    }

    fun startGame() {
        gameContext.isRunning = true
    }

    override fun run() {
        //Execute game
        while(gameContext.isRunning){

        }
    }

    fun pause() {
        gameContext.isRunning = false
    }

    fun resume() {
        gameContext.isRunning = true
    }

    fun finish() {
        gameContext.isRunning = false
    }
}