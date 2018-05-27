package br.com.renanbarbieri.snakotlin.presentation.engine

import android.view.SurfaceHolder
import br.com.renanbarbieri.snakotlin.presentation.engine.model.CanvasCircle

interface GameScreenDrawerInteractor {

    interface Input {
        fun drawFrame(
                backgroundColor: Int, foodColor: Int, snakeColor: Int, scoreColor: Int,
                snakeBody: ArrayList<CanvasCircle>,
                food: CanvasCircle, score: Int, holder: SurfaceHolder )
    }
}