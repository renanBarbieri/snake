package br.com.renanbarbieri.snakotlin.domain.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
class Score {
    @PrimaryKey(autoGenerate = true) var id: Int = 0
    var value: Int = 0
}