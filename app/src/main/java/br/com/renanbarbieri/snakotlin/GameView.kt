package br.com.renanbarbieri.snakotlin

import android.content.Context
import android.graphics.Point
import android.view.SurfaceView
import br.com.renanbarbieri.snakotlin.model.GameContext
import br.com.renanbarbieri.snakotlin.model.GameMap

class GameView(context: Context): SurfaceView(context), Runnable {

    private val millisInSecond: Long = 1000
    private var nowTime: Long = System.currentTimeMillis()

    // Game Map instance
    private var map: GameMap? = null
    // Game Context instance
    private val gameContext: GameContext = GameContext()

    // Time for verify update
    private var nextUpdateTime: Long = nowTime

    private val fps: Long = 10


    constructor(context: Context, screenDimen: Point): this(context) {
        this.map = GameMap(width = screenDimen.x, height = screenDimen.y)
    }

    fun startGame() {
        gameContext.isRunning = true
    }

    override fun run() {
        //Execute game
        while(gameContext.isRunning){
            if(updateGame()){

            }
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

    fun updateGame(): Boolean {
        var needUpdate = false
        if(nextUpdateTime < nowTime){
            nextUpdateTime = nowTime + (millisInSecond/fps)
            needUpdate = true
        }

        return needUpdate
    }
}