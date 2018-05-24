package br.com.renanbarbieri.snakotlin

import android.view.GestureDetector
import android.view.MotionEvent

class GestureDetectorListener(): GestureDetector.SimpleOnGestureListener() {

    private var gestureDirectionListener: GestureDirectionListener? = null
    private var minSwipe: Int? = null
    private var maxSwipe: Int? = null

    constructor(minSwipe: Int, maxSwipe: Int?, directionListener: GestureDirectionListener): this(){
        this.minSwipe = minSwipe
        this.maxSwipe = maxSwipe
        this.gestureDirectionListener = directionListener
    }

    override fun onFling(startEvent: MotionEvent?, currentEvent: MotionEvent?, velocityX: Float, velocityY: Float): Boolean {
        if(startEvent != null && currentEvent != null) {
            this.maxSwipe?.let {
                if (Math.abs(startEvent.y - currentEvent.y) > it)
                    return false
            }

            this.minSwipe?.let {
                if(startEvent.x - currentEvent.x > it) {
                    gestureDirectionListener?.onSwipeLeft()
                }
                if (currentEvent.x - startEvent.x > it) {
                    gestureDirectionListener?.onSwipeRight()
                }
                if(startEvent.y - currentEvent.y > it) {
                    gestureDirectionListener?.onSwipeDown()
                }
                if (currentEvent.y - startEvent.y > it) {
                    gestureDirectionListener?.onSwipeUp()
                }
            }
        }

      return false
    }

    override fun onDown(e: MotionEvent?): Boolean {
        return true
    }

    interface GestureDirectionListener {
        /**
         * Called when user swipe to left
         */
        fun onSwipeLeft()
        /**
         * Called when user swipe to right
         */
        fun onSwipeRight()
        /**
         * Called when user swipe to top
         */
        fun onSwipeUp()
        /**
         * Called when user swipe to bottom
         */
        fun onSwipeDown()
    }
}