package br.com.renanbarbieri.snakotlin.model


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

    init{
        this.blocksX = width/numOfBlocksWidth
        this.blocksY = height/numOfBlocksHeight
    }
}