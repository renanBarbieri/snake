package br.com.renanbarbieri.snakotlin.model

import br.com.renanbarbieri.snakotlin.Direction
import br.com.renanbarbieri.snakotlin.random

/**
 * This class is responsible to describe the game map attributes
 */
class GameMap(width: Int, height: Int) {

    //Constants to set map
    private val numOfBlocksWidth: Int = 20
    private val numOfBlocksHeight: Int = 30
    private val maxShotRandomFood: Int = 100

    /**
     * Quantity of blocks available on each axis
     */
    private var blocksX: Int = 0
    private var blocksY: Int = 0

    private var snakeX: IntArray = IntArray(numOfBlocksWidth)
    private var snakeY: IntArray = IntArray(numOfBlocksHeight)

    private var foodX: Int = 0
    private var foodY: Int = 0

    private val snake: Snake = Snake()
    private val food: Food = Food()

    init{
        // defines number of blocks
        this.blocksX = width/numOfBlocksWidth
        this.blocksY = height/numOfBlocksHeight

        putSnakeOnCenter()
        generateFoodPosition()
    }

    /**
     * Defines the position of snake head
     */
    private fun putSnakeOnCenter() {
        // init snake on center
        snakeX[0] = numOfBlocksWidth/2
        snakeY[0] = numOfBlocksHeight/2
    }

    /**
     * Generate a new position for food
     */
    private fun generateFoodPosition(shotTurn: Int = 0) {
        val newXPosition: Int = random(from = 0, to = numOfBlocksWidth)
        val newYPosition: Int = random(from = 0, to = numOfBlocksHeight)

        if(hasSnakeAtPosition(newXPosition, newYPosition)) {
            if(shotTurn <= maxShotRandomFood) {
                val nextShot = 1 + shotTurn
                generateFoodPosition(nextShot)
                return
            }
            else {
                //to avoid infinity loop
            }
        }

        foodX = newXPosition
        foodY = newYPosition
    }

    /**
     * Verifies if has snake at position (x,y)
     * @param x position x
     * @param y position y
     * @return boolean : true if has some part of snake at position
     */
    private fun hasSnakeAtPosition(x: Int, y: Int): Boolean = snakeX.contains(x) && snakeY.contains(y)

    /**
     * Verifies is the snake is above food position
     * @return boolean : true if the snake is above food position
     */
    fun snakeAteFood(): Boolean = (hasSnakeAtPosition(x = foodX, y = foodY))

    /**
     * Deal with snake food
     */
    fun feedSnake() {
        snake.growUp(getFoodValue())
        generateFoodPosition()
    }

    /**
     * Get de food value
     */
    fun getFoodValue() = food.point

    /**
     * Update de snake body on map
     * @param direction: the direction of snake head
     */
    fun updateSnakePosition(direction: Direction){
        // update the snake body
        if(snake.length > 1) {
            for (i in (snake.length-1) downTo 1) {
                //update position from end to start
                snakeX[i] = snakeX[i - 1]
                snakeY[i] = snakeY[i - 1]

            }
        }

        // update the snake head position
        when(direction) {
            Direction.LEFT -> snakeX[0]--
            Direction.RIGHT -> snakeX[0]++
            Direction.UP -> snakeY[0]--
            Direction.DOWN -> snakeY[0]++
        }
    }

    /**
     * Retrieves the snake body position at map
     * @return {ArrayList<CanvasCircle>} list of snake body
     */
    fun getSnakeBody(): ArrayList<CanvasCircle> {
        val canvasArray: ArrayList<CanvasCircle> = arrayListOf()
        for(i in 0 until snake.length) {
            val startXRect: Float = (snakeX[i] * blocksX).toFloat()
            val endXRect: Float = ((snakeX[i] * blocksX) + blocksX).toFloat()

            val startYRect: Float = (snakeY[i] * blocksY).toFloat()
            val endYRect: Float = ((snakeY[i] * blocksY) + blocksY).toFloat()
            canvasArray.add(CanvasCircle(
                    centerX = (startXRect + endXRect)/2,
                    centerY = (startYRect + endYRect)/2,
                    radius = (blocksX/2).toFloat()
            ))
        }
        return canvasArray
    }

    /**
     * Retrieves the food position at map
     */
    fun getFood(): CanvasCircle {
        val startXRect: Float = (foodX * blocksX).toFloat()
        val endXRect: Float = ((foodX * blocksX) + blocksX).toFloat()

        val startYRect: Float = (foodY * blocksY).toFloat()
        val endYRect: Float = ((foodY * blocksY) + blocksY).toFloat()

        return CanvasCircle(
                centerX = (startXRect + endXRect)/2,
                centerY = (startYRect + endYRect)/2,
                radius = (blocksX/2).toFloat()

        )
    }

    /**
     * Verifies if has some collision
     */
    fun hasCollision(): Boolean = this.hasWallCollision() || this.hasSnakeCollision()

    /**
     * Verifies if the head of snake is out of map
     */
    private fun hasWallCollision(): Boolean =
        snakeX[0] == -1 || snakeY[0] == -1 || snakeX[0] >= numOfBlocksWidth || snakeY[0] >= numOfBlocksHeight


    /**
     * Verifies if has two parts of snake at same position
     */
    private fun hasSnakeCollision(): Boolean {
        var response: Boolean = false
        for(i in 1 until snake.length){
            if(snakeX[0] == snakeX[i] && snakeY[0] == snakeY[i])
                response = true
        }

        return response
    }

    fun clear() {
        snake.length = 1
        snakeX = IntArray(numOfBlocksWidth)
        snakeY = IntArray(numOfBlocksHeight)
        putSnakeOnCenter()
        generateFoodPosition()
    }

}