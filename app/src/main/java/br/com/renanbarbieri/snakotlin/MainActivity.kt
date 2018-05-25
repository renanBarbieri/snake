package br.com.renanbarbieri.snakotlin

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    companion object {
        val backToMainMenuRequest: Int = 789
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btStart.setOnClickListener({
            startActivityForResult(Intent(this, GameActivity::class.java), MainActivity.backToMainMenuRequest)
        })
    }
}
