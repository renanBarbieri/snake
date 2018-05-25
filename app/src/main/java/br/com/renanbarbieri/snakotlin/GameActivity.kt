package br.com.renanbarbieri.snakotlin

import android.graphics.Point
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import br.com.renanbarbieri.snakotlin.engine.GameEngine
import br.com.renanbarbieri.snakotlin.framework.gestureDirection.GestureDetectorFramework
import br.com.renanbarbieri.snakotlin.framework.screenDrawer.ScreenDrawerFramework

class GameActivity : AppCompatActivity() {

    private var gameEngine: GameEngine? = null
    private var gestureInteractor: GestureDetectorFramework? = null
    private var screenDrawer: ScreenDrawerFramework? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)

        this.initFramework(screenX = size.x, screenY = size.y)

        gameEngine = GameEngine(
                    context = this,
                    screenDimen = size,
                    drawerInteractor = this.screenDrawer,
                    gameGestureInteractor = this.gestureInteractor
                )

        setContentView(gameEngine)
    }

    private fun initFramework(screenX:Int, screenY: Int) {
        this.gestureInteractor = GestureDetectorFramework(
                minSwipe = screenX/4,
                maxSwipe = null
        )
        this.screenDrawer = ScreenDrawerFramework
    }

    override fun onResume() {
        super.onResume()
        gameEngine?.resume()
    }

    override fun onPause() {
        super.onPause()
        gameEngine?.pause()
    }
}
