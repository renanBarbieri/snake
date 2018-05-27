package br.com.renanbarbieri.snakotlin.presentation.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import br.com.renanbarbieri.snakotlin.R
import br.com.renanbarbieri.snakotlin.domain.entity.Score
import br.com.renanbarbieri.snakotlin.domain.getUserTopScore.GetUserTopScoreViewModel
import br.com.renanbarbieri.snakotlin.guard
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var viewModel: GetUserTopScoreViewModel? = null
    private var clickCount: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btStart.setOnClickListener({
            val level: GameActivity.Companion.Level = when(clickCount.rem(3)){
                0 -> GameActivity.Companion.Level.NORMAL
                1 -> GameActivity.Companion.Level.HARD
                else -> GameActivity.Companion.Level.EASY
            }
            GameActivity.start(level, this)
        })

        // set listener for define the level
        btLevel.setOnClickListener {
            clickCount++
            when(clickCount.rem(3)){
                0 -> btLevel.setText(R.string.normal)
                1 -> btLevel.setText(R.string.hard)
                else -> btLevel.setText(R.string.easy)
            }
        }

        viewModel = ViewModelProviders.of(this).get(GetUserTopScoreViewModel::class.java)
    }

    override fun onResume() {
        super.onResume()
        viewModel?.getTopScore()?.observe(this, Observer<Score> {
            it.guard {
                tvTopScore.text = "0"
            }?.let {
                tvTopScore.text = it.value.toString()
            }
        })
    }
}
