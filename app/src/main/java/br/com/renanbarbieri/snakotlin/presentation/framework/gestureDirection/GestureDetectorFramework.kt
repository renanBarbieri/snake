package br.com.renanbarbieri.snakotlin.presentation.framework.gestureDirection

import android.view.GestureDetector
import android.view.MotionEvent
import br.com.renanbarbieri.snakotlin.presentation.engine.GameGestureDirectionInteractor

/**
 * Class for handle user interaction
 */
class GestureDetectorFramework(): GestureDetector.SimpleOnGestureListener(), GameGestureDirectionInteractor.Input {

    private var gameGestureDirectionListener: GameGestureDirectionInteractor.Output? = null
    private var minSwipe: Int? = null
    private var maxSwipe: Int? = null

    constructor(minSwipe: Int, maxSwipe: Int?): this(){
        this.minSwipe = minSwipe
        this.maxSwipe = maxSwipe
    }

    override fun detectMovement(event: MotionEvent, detector: GestureDetector,
                                interactorOutput: GameGestureDirectionInteractor.Output) {
        this.gameGestureDirectionListener = interactorOutput
        detector.onTouchEvent(event)
    }

    override fun onFling(startEvent: MotionEvent?, currentEvent: MotionEvent?, velocityX: Float, velocityY: Float): Boolean {
        if(startEvent != null && currentEvent != null) {
            this.maxSwipe?.let {
                if (Math.abs(startEvent.y - currentEvent.y) > it)
                    return false
            }

            this.minSwipe?.let {
                if(startEvent.x - currentEvent.x > it) {
                    gameGestureDirectionListener?.onSwipeLeft()
                }
                if (currentEvent.x - startEvent.x > it) {
                    gameGestureDirectionListener?.onSwipeRight()
                }
                if(startEvent.y - currentEvent.y > it) {
                    gameGestureDirectionListener?.onSwipeUp()
                }
                if (currentEvent.y - startEvent.y > it) {
                    gameGestureDirectionListener?.onSwipeDown()
                }
            }
        }

      return false
    }

    override fun onDown(e: MotionEvent?): Boolean {
        return true
    }
}