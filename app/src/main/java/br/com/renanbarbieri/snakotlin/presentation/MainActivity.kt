package br.com.renanbarbieri.snakotlin.presentation

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import br.com.renanbarbieri.snakotlin.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btStart.setOnClickListener({
            startActivity(Intent(this, GameActivity::class.java))
        })

        //TODO: escutar modificação no ROOM
    }
}
