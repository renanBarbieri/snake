package br.com.renanbarbieri.snakotlin.engine

import android.content.Context
import android.graphics.Point
import android.support.v4.content.ContextCompat
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.SurfaceView
import br.com.renanbarbieri.snakotlin.engine.model.Direction
import br.com.renanbarbieri.snakotlin.R
import br.com.renanbarbieri.snakotlin.engine.model.GameContext
import br.com.renanbarbieri.snakotlin.engine.model.GameMap

class GameEngine(context: Context): SurfaceView(context), Runnable, GameGestureDirectionInteractor.Output {

    private var gameGestureInteractor: GameGestureDirectionInteractor.Input? = null
    private var gestureDetector: GestureDetector? = null

    private var drawerInteractor: GameScreenDrawerInteractor.Input? = null

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

    private var currentDirection: Direction = Direction.UP

    constructor(context: Context, screenDimen: Point,
                drawerInteractor: GameScreenDrawerInteractor.Input?,
                gameGestureInteractor: GameGestureDirectionInteractor.Input?): this(context) {
        this.map = GameMap(width = screenDimen.x, height = screenDimen.y)
        this.screenDimen = screenDimen

        this.initInteractors(drawerInteractor, gameGestureInteractor)
    }

    private fun initInteractors(
            drawerInteractor: GameScreenDrawerInteractor.Input?,
            gameGestureInteractor: GameGestureDirectionInteractor.Input?) {
        this.drawerInteractor = drawerInteractor
        this.gameGestureInteractor = gameGestureInteractor
        this.gestureDetector = GestureDetector(context,gameGestureInteractor)
    }

    /**
     * Restarts the game
     */
    private fun restartGame() {
        map?.clear()
        currentDirection = Direction.UP
        gameContext.score = 0
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
                this.gestureDetector?.let {
                    gameGestureInteractor?.detectMovement(
                            event = event,
                            interactorOutput = this,
                            detector = this.gestureDetector!!)
                }

            else
                restartGame()
        }
        return true
    }

    /**
     * implements GestureDetectorFramework.GestureDirectionListener.Output
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
     * implements GestureDetectorFramework.GestureDirectionListener.Output
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
     * implements GestureDetectorFramework.GestureDirectionListener.Output
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
     * implements GestureDetectorFramework.GestureDirectionListener.Output
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
            map?.let {
                drawerInteractor?.drawFrame(
                        backgroundColor = ContextCompat.getColor(context, R.color.gameBackground),
                        foodColor = ContextCompat.getColor(context, R.color.food),
                        food = it.getFood(),
                        snakeColor = ContextCompat.getColor(context, R.color.snake),
                        snakeBody = it.getSnakeBody(),
                        scoreColor = ContextCompat.getColor(context, R.color.score),
                        score = this.gameContext.score,
                        holder = holder
                )
            }
        }
    }
}