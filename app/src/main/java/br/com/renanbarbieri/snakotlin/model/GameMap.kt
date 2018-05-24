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

    /**
     * Quantity of blocks available on each axis
     */
    private var blocksX: Int = 0
    private var blocksY: Int = 0

    private var snakeX: IntArray
    private var snakeY: IntArray

    private var foodX: Int = 0
    private var foodY: Int = 0

    private val snake: Snake = Snake()
    private val food: Food = Food()

    init{
        // defines number of blocks
        this.blocksX = width/numOfBlocksWidth
        this.blocksY = height/numOfBlocksHeight

        // init snake on center
        snakeX = intArrayOf(numOfBlocksWidth/2)
        snakeY = intArrayOf(numOfBlocksHeight/2)

        generateFoodPosition()
    }

    /**
     * Generate a new position for food
     */
    private fun generateFoodPosition() {
        val newXPosition: Int = random(from = 0, to = numOfBlocksWidth)
        val newYPosition: Int = random(from = 0, to = numOfBlocksHeight)

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
     *
     */
    fun feedSnake() {
        snake.growUp(getFoodValue())
        generateFoodPosition()
    }

    fun getFoodValue() = food.point

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
            Direction.UP -> snakeY[0]++
            Direction.DOWN -> snakeY[0]--
        }
    }

    fun getSnakeBody(): ArrayList<CanvasSquare> {
        val canvasArray: ArrayList<CanvasSquare> = arrayListOf()
        for(i in 0 until snake.length) {
            canvasArray.add(CanvasSquare(
                    left = (snakeX[i] * blocksX).toFloat(),
                    right = ((snakeX[i] * blocksX) + blocksX).toFloat(),
                    top = (snakeY[i] * blocksY).toFloat(),
                    bottom = ((snakeY[i] * blocksY) + blocksY).toFloat()
            ))
        }
        return canvasArray
    }

}