package br.com.renanbarbieri.snakotlin.presentation.ui

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.graphics.Point
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import br.com.renanbarbieri.snakotlin.R
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

    companion object {

        val ARG_LEVEL = "br.com.renanbarbieri.snakotlin.presentation.ui.GameActivity.ARG_LEVEL"

        enum class Level { EASY, NORMAL, HARD }

        /**
         * Static method for start this activity with necessary parameters
         */
        fun start(level: Level, callerActivity: AppCompatActivity){
            val intent = Intent(callerActivity, GameActivity::class.java)
            val args = Bundle()
            args.putInt(ARG_LEVEL, level.ordinal)
            intent.putExtras(args)
            callerActivity.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)

        this.initFramework(screenX = size.x)

        var fps: Long = 6

        intent.extras?.let {
           fps = when(it.getInt(ARG_LEVEL)){
               Level.EASY.ordinal -> 3
               Level.HARD.ordinal -> 9
               else -> 6
           }
        }

        gameEngine = GameEngine(
                    context = this,
                    fps = fps,
                    screenDimen = size,
                    drawerInteractor = this.screenDrawer,
                    gameGestureInteractor = this.gestureInteractor,
                    gameLifecycle = this
                )

        viewModel = ViewModelProviders.of(this).get(SaveUserScoreViewModel::class.java)

        setContentView(gameEngine)
    }

    /**
     * Initialize the frameworks
     */
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

    /**
     * When snake is dead, this method is called.
     * - Save the score
     */
    override fun onSnakeDead(score: Int) {
        viewModel?.saveScore(score)
        runOnUiThread({this.showTryAgain()})
    }

    override fun onGameFinished() {
        runOnUiThread({this.showGameFinished()})
    }

    override fun onError(errorMessage: String) {
        runOnUiThread({this.showErrorAlert(errorMessage)})
    }


    private fun showTryAgain(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.dialogTitleFail)
        builder.setMessage(R.string.snakeIsDead)
        builder.setPositiveButton(R.string.tryAgain){dialog, which ->
            gameEngine?.restartGame()
        }
        builder.setNeutralButton(R.string.goBack){dialog, which ->
            this.onBackPressed()
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun showGameFinished() {
        val builder = AlertDialog.Builder(this)

        builder.setTitle(R.string.dialogTitleSuccess)
        builder.setMessage(R.string.snakeIsTooBig)
        builder.setPositiveButton(R.string.success){dialog, which ->
            this.onBackPressed()
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun showErrorAlert(message: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.dialogTitleError)
        builder.setMessage(message)
        builder.setPositiveButton(R.string.ok){dialog, which ->
            this.onBackPressed()
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}
