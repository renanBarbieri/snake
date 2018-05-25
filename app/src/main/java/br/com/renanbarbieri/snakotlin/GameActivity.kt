package br.com.renanbarbieri.snakotlin

import android.graphics.Point
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import br.com.renanbarbieri.snakotlin.engine.GameEngine

class GameActivity : AppCompatActivity() {

    private var gameEngine: GameEngine? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        gameEngine = GameEngine(context = this, screenDimen = size)

        setContentView(gameEngine)
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
