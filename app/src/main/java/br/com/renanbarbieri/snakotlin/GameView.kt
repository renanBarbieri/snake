package br.com.renanbarbieri.snakotlin

import android.content.Context
import android.graphics.Point
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.SurfaceView
import br.com.renanbarbieri.snakotlin.model.GameContext
import br.com.renanbarbieri.snakotlin.model.GameMap

class GameView(context: Context): SurfaceView(context), Runnable, GestureDetectorListener.GestureDirectionListener {

    private val millisInSecond: Long = 1000
    private var nowTime: Long = System.currentTimeMillis()

    // Game Map instance
    private var map: GameMap? = null
    // Game Context instance
    private val gameContext: GameContext = GameContext()

    // Time for verify update
    private var nextUpdateTime: Long = nowTime

    private val fps: Long = 10
    private var screenDimen: Point? = null

    private var gestureDetector: GestureDetector? = null

    private var currentDirection: Direction = Direction.UP

    constructor(context: Context, screenDimen: Point): this(context) {
        this.map = GameMap(width = screenDimen.x, height = screenDimen.y)
        this.screenDimen = screenDimen

        val minSwipe = screenDimen.x/3

        gestureDetector = GestureDetector(context, GestureDetectorListener(minSwipe = minSwipe, maxSwipe = null, directionListener = this))
    }

    /**
     * Habilita o início do jogo
     */
    fun startGame() {
        gameContext.isRunning = true
    }

    override fun run() {
        //Execute game
        while(gameContext.isRunning){
            if(updateFrame()){
                updateGameContext()
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

    /**
     * Verifica se é necessário realizar o update do frame do jogo
     * @return boolean : true caso seja necessário realizar o update
     */
    private fun updateFrame(): Boolean {
        var needUpdate = false
        if(nextUpdateTime < nowTime){
            nextUpdateTime = nowTime + (millisInSecond/fps)
            needUpdate = true
        }

        return needUpdate
    }

    private fun updateGameContext() {
        this.map?.let {
            if(it.snakeAteFood()){
                //snake should eat food
                this.gameContext.updateScore(addValue = it.getFoodValue())
                it.feedSnake()

            }
            moveSnake()
        }

    }

    private fun moveSnake() {

    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event?.let {
            gestureDetector?.onTouchEvent(it)
        }
        return true
    }

    override fun onSwipeLeft() {
        when(currentDirection) {
            Direction.UP,
            Direction.DOWN -> {
                currentDirection = Direction.LEFT
            }
            Direction.LEFT,
            Direction.RIGHT -> {
                //do nothing
            }
        }
    }

    override fun onSwipeRight() {
        when(currentDirection) {
            Direction.UP,
            Direction.DOWN -> {
                currentDirection = Direction.RIGHT
            }
            Direction.LEFT,
            Direction.RIGHT -> {
                //do nothing
            }
        }
    }

    override fun onSwipeUp() {
        when(currentDirection) {
            Direction.UP,
            Direction.DOWN -> {
                //do nothing
            }
            Direction.LEFT,
            Direction.RIGHT -> {
                currentDirection = Direction.UP
            }
        }
    }

    override fun onSwipeDown() {
        when(currentDirection) {
            Direction.UP,
            Direction.DOWN -> {
                //do nothing
            }
            Direction.LEFT,
            Direction.RIGHT -> {
                currentDirection = Direction.DOWN
            }
        }
    }
}