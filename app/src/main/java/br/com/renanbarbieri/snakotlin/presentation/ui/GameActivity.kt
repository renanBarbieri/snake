package br.com.renanbarbieri.snakotlin.presentation.ui

import android.arch.lifecycle.ViewModelProviders
import android.graphics.Point
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import br.com.renanbarbieri.snakotlin.domain.saveUserScore.SaveUserScoreViewModel
import br.com.renanbarbieri.snakotlin.presentation.engine.GameLifecycle
import br.com.renanbarbieri.snakotlin.presentation.engine.GameEngine
import br.com.renanbarbieri.snakotlin.presentation.framework.gestureDirection.GestureDetectorFramework
import br.com.renanbarbieri.snakotlin.presentation.framework.screenDrawer.ScreenDrawerFramework

class GameActivity : AppCompatActivity(), GameLifecycle {

    private var gameEngine: GameEngine? = null
    private var gestureInteractor: GestureDetectorFramework? = null
    private var screenDrawer: ScreenDrawerFramework? = null
    private var viewModel: SaveUserScoreViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)

        this.initFramework(screenX = size.x)

        gameEngine = GameEngine(
                    context = this,
                    screenDimen = size,
                    drawerInteractor = this.screenDrawer,
                    gameGestureInteractor = this.gestureInteractor,
                    gameLifecycle = this
                )

        viewModel = ViewModelProviders.of(this).get(SaveUserScoreViewModel::class.java)

        setContentView(gameEngine)
    }

    private fun initFramework(screenX:Int) {
        this.gestureInteractor = GestureDetectorFramework(
                minSwipe = screenX/5,
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

    override fun onSnakeDead(score: Int) {
        viewModel?.saveScore(score)
    }

    override fun onError(errorMessage: String) {
        //TODO: exibir alerta de erro
    }
}
