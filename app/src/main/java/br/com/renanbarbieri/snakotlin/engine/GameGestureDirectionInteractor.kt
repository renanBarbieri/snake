package br.com.renanbarbieri.snakotlin.engine

import android.view.GestureDetector
import android.view.MotionEvent

interface GameGestureDirectionInteractor {

    interface Input: GestureDetector.OnGestureListener{
        /**
         * Called for detect movement
         */
        fun detectMovement(event: MotionEvent, detector: GestureDetector, interactorOutput: Output)
    }

    interface Output {
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