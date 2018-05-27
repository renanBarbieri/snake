package br.com.renanbarbieri.snakotlin.presentation.engine

import android.content.Context
import android.graphics.Point
import android.support.v4.content.ContextCompat
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.SurfaceView
import br.com.renanbarbieri.snakotlin.presentation.engine.model.Direction
import br.com.renanbarbieri.snakotlin.R
import br.com.renanbarbieri.snakotlin.presentation.engine.model.GameContext
import br.com.renanbarbieri.snakotlin.presentation.engine.model.GameMap

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

    private var fps: Long = 6
    private var screenDimen: Point? = null

    private var currentDirection: Direction = Direction.UP

    var gameLifecycle: GameLifecycle? = null

    constructor(context: Context, screenDimen: Point,
                drawerInteractor: GameScreenDrawerInteractor.Input?,
                gameGestureInteractor: GameGestureDirectionInteractor.Input?): this(context) {
        this.map = GameMap(width = screenDimen.x, height = screenDimen.y)
        this.screenDimen = screenDimen

        this.initInteractors(drawerInteractor, gameGestureInteractor)
    }


    constructor(context: Context, fps: Long, screenDimen: Point,
                drawerInteractor: GameScreenDrawerInteractor.Input?,
                gameGestureInteractor: GameGestureDirectionInteractor.Input?,
                gameLifecycle: GameLifecycle): this(context) {
        this.map = GameMap(width = screenDimen.x, height = screenDimen.y)
        this.screenDimen = screenDimen
        this.gameLifecycle = gameLifecycle
        this.fps = fps
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
        this.map?.clear()
        this.currentDirection = Direction.UP
        this.gameContext.score = 0
        resume()
    }

    override fun run() {
        //Execute game
        while(this.gameContext.isRunning){
            if(updateFrame()){
                updateGameContext()
                drawFrame()
            }
        }
    }

    fun pause() {
        try {
            this.gameContext.isRunning = false
            this.gameThread?.join()
        } catch (error: InterruptedException){
            gameLifecycle?.onError(errorMessage = context.getString(R.string.errorWhenPausing))
        }

    }

    fun resume() {
        this.gameContext.isRunning = true
        this.gameThread = Thread(this)
        this.gameThread?.start()
    }

    fun finish() {
        this.gameContext.isRunning = false
        this.gameLifecycle?.onSnakeDead(this.gameContext.score)
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
                    this.gameGestureInteractor?.detectMovement(
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
        when(this.currentDirection) {
            Direction.UP,
            Direction.DOWN -> {
                this.currentDirection = Direction.LEFT
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
        when(this.currentDirection) {
            Direction.UP,
            Direction.DOWN -> {
                this.currentDirection = Direction.RIGHT
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
        when(this.currentDirection) {
            Direction.UP,
            Direction.DOWN -> {
                //do nothing
            }
            Direction.LEFT,
            Direction.RIGHT -> {
                this.currentDirection = Direction.UP
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
                this.currentDirection = Direction.DOWN
            }
        }
    }

    private fun drawFrame() {
        if(this.holder.surface.isValid) {
            this.map?.let {
                this.drawerInteractor?.drawFrame(
                        backgroundColor = ContextCompat.getColor(this.context, R.color.gameBackground),
                        foodColor = ContextCompat.getColor(this.context, R.color.food),
                        food = it.getFood(),
                        snakeColor = ContextCompat.getColor(this.context, R.color.snake),
                        snakeBody = it.getSnakeBody(),
                        scoreColor = ContextCompat.getColor(this.context, R.color.score),
                        score = this.gameContext.score,
                        holder = this.holder
                )
            }
        }
    }
}