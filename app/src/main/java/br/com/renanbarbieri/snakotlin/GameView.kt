package br.com.renanbarbieri.snakotlin

import android.content.Context
import android.graphics.Paint
import android.graphics.Point
import android.view.SurfaceView
import br.com.renanbarbieri.snakotlin.model.GameMap

class GameView(context: Context): SurfaceView(context) {

    // Game Map instance
    var map: GameMap? = null

    var paint: Paint = Paint()

    constructor(context: Context, screenDimen: Point): this(context) {
        this.map = GameMap(width = screenDimen.x, height = screenDimen.y)
    }

}