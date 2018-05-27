package br.com.renanbarbieri.snakotlin.presentation.engine

import android.view.SurfaceHolder
import br.com.renanbarbieri.snakotlin.presentation.engine.model.CanvasCircle

interface GameScreenDrawerInteractor {

    interface Input {

        /**
         * Called for draw all map content
         * @param backgroundColor the background color
         * @param foodColor the food color
         * @param snakeColor the snake color
         * @param scoreColor the score color
         * @param snakeBody the snakebody points
         * @param food the food point
         * @param score the user score
         * @param holder the surfaceHolder
         */
        fun drawFrame(
                backgroundColor: Int, foodColor: Int, snakeColor: Int, scoreColor: Int,
                snakeBody: ArrayList<CanvasCircle>,
                food: CanvasCircle, score: Int, holder: SurfaceHolder )
    }
}