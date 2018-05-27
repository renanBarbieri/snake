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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btStart.setOnClickListener({
            startActivity(Intent(this, GameActivity::class.java))
        })

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
