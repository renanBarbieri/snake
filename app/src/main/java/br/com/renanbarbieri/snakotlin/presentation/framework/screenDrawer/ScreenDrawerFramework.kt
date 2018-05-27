package br.com.renanbarbieri.snakotlin.presentation.framework.screenDrawer

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Typeface
import android.view.SurfaceHolder
import br.com.renanbarbieri.snakotlin.presentation.engine.GameScreenDrawerInteractor
import br.com.renanbarbieri.snakotlin.presentation.engine.model.CanvasCircle

object ScreenDrawerFramework: GameScreenDrawerInteractor.Input {

    private var canvas: Canvas? = null
    private val paint: Paint = Paint()

    override fun drawFrame(backgroundColor: Int, foodColor: Int, snakeColor: Int, scoreColor: Int,
                           snakeBody: ArrayList<CanvasCircle>, food: CanvasCircle,
                           score: Int, holder: SurfaceHolder) {
        canvas = holder.lockCanvas()

        canvas?.let {
            //color background
            it.drawColor(backgroundColor)

            paint.color = foodColor
            canvas?.drawCircle(food.centerX, food.centerY, food.radius, paint)

            paint.color = snakeColor
            snakeBody.forEach { canvas?.drawCircle(it.centerX, it.centerY, it.radius, paint) }

            paint.color = scoreColor
            paint.textSize = 80f
            paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
            canvas?.drawText(score.toString(), 45.0f, 90.0f, paint)

        }

        holder.unlockCanvasAndPost(canvas)
    }
}