package br.com.renanbarbieri.snakotlin

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Point
import android.support.v4.content.ContextCompat
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.SurfaceView
import br.com.renanbarbieri.snakotlin.model.GameContext
import br.com.renanbarbieri.snakotlin.model.GameMap

class GameView(context: Context): SurfaceView(context), Runnable, GestureDetectorListener.GestureDirectionListener {

    private var canvas: Canvas? = null
    private val paintSnake: Paint = Paint()
    private val paintFood: Paint = Paint()

    private var gameThread: Thread? = null

    private val millisInSecond: Long = 1000
    private var nowTime: Long = System.currentTimeMillis()

    // Game Map instance
    private var map: GameMap? = null
    // Game Context instance
    private val gameContext: GameContext = GameContext()

    // Time for verify update
    private var nextUpdateTime: Long = nowTime

    private val fps: Long = 6
    private var screenDimen: Point? = null

    private var gestureDetector: GestureDetector? = null

    private var currentDirection: Direction = Direction.UP

    constructor(context: Context, screenDimen: Point): this(context) {
        this.map = GameMap(width = screenDimen.x, height = screenDimen.y)
        this.screenDimen = screenDimen

        val minSwipe = screenDimen.x/4

        gestureDetector = GestureDetector(context, GestureDetectorListener(minSwipe = minSwipe, maxSwipe = null, directionListener = this))
    }

    /**
     * Restarts the game
     */
    fun restartGame() {
        map?.clear()
        currentDirection = Direction.UP
//        gameContext.isRunning = true
        resume()
    }

    override fun run() {
        //Execute game
        while(gameContext.isRunning){
            if(updateFrame()){
                updateGameContext()
                drawFrame()
            }
        }
    }

    fun pause() {
        try {
            gameContext.isRunning = false
            gameThread?.join()
        } catch (erro: InterruptedException){

        }

    }

    fun resume() {
        gameContext.isRunning = true
        gameThread = Thread(this)
        gameThread?.start()
    }

    fun finish() {
        gameContext.isRunning = false
    }

    /**
     * Verifies if its necessery update frame
     * @return boolean : true if its necessary
     */
    private fun updateFrame(): Boolean {
        var needUpdate = false
        if(nextUpdateTime <= System.currentTimeMillis()){
            nextUpdateTime = System.currentTimeMillis() + (millisInSecond/fps)
            needUpdate = true
        }

        return needUpdate
    }

    /**
     * Update all elements at screen
     */
    private fun updateGameContext() {
        this.map?.let {
            if(it.snakeAteFood()){
                //snake should eat food
                this.gameContext.updateScore(addValue = it.getFoodValue())
                it.feedSnake()

            }
            it.updateSnakePosition(currentDirection)

            if(it.hasCollision()) {
                finish()
            }
        }

    }

    /**
     * Verifies user touch event
     */
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event?.let {
            if(this.gameContext.isRunning)
                gestureDetector?.onTouchEvent(it)
            else
                restartGame()
        }
        return true
    }

    /**
     * implements GestureDetectorListener.GestureDirectionListener
     */
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

    /**
     * implements GestureDetectorListener.GestureDirectionListener
     */
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

    /**
     * implements GestureDetectorListener.GestureDirectionListener
     */
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

    /**
     * implements GestureDetectorListener.GestureDirectionListener
     */
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

    private fun drawFrame() {
        if(holder.surface.isValid) {
            canvas = holder.lockCanvas()
            paintSnake.color = ContextCompat.getColor(context, R.color.snake)
            paintFood.color = ContextCompat.getColor(context, R.color.food)
            canvas?.let {
                //color background
                it.drawColor(ContextCompat.getColor(context, R.color.gameBackground))
                map?.getFood()?.let { canvas?.drawCircle(it.centerX, it.centerY, it.radius,paintFood) }
                map?.getSnakeBody()?.forEach { canvas?.drawCircle(it.centerX, it.centerY, it.radius, paintSnake) }

            }

            holder.unlockCanvasAndPost(canvas)
        }
    }
}