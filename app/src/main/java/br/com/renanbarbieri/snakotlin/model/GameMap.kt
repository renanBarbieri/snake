package br.com.renanbarbieri.snakotlin.model

import br.com.renanbarbieri.snakotlin.random
import java.util.*

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
     * Gera uma nova posição para a comida
     */
    private fun generateFoodPosition() {
        val newXPosition: Int = random(from = 0, to = numOfBlocksWidth)
        val newYPosition: Int = random(from = 0, to = numOfBlocksHeight)

        foodX = newXPosition
        foodY = newYPosition
    }

    /**
     * Verifica se a cobra está na posição x e y
     * @param x posição x
     * @param y posição y
     * @return boolean : true caso tenha cobra na posição
     */
    private fun hasSnakeAtPosition(x: Int, y: Int): Boolean = snakeX.contains(x) && snakeY.contains(y)

    /**
     * Verifica se a cobra está em cima da comida
     * @return boolean : true caso a cobra esteja em cima da comida
     */
    fun snakeAteFood(): Boolean = (hasSnakeAtPosition(x= foodX, y= foodY))

    /**
     *
     */
    fun feedSnake() {
        snake.growUp(getFoodValue())
        generateFoodPosition()
    }

    fun getFoodValue() = food.point

}