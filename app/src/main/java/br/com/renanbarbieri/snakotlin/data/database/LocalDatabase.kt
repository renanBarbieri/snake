package br.com.renanbarbieri.snakotlin.data.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import br.com.renanbarbieri.snakotlin.data.database.dao.ScoreDao
import br.com.renanbarbieri.snakotlin.domain.entity.Score

@Database(
        entities = [
            Score::class
        ],
        version = 1
)
abstract class LocalDatabase: RoomDatabase() {
    abstract fun scoreDao(): ScoreDao

    companion object {
        private var INSTANCE: LocalDatabase? = null

        fun getInstance(context: Context): LocalDatabase? {
            if (INSTANCE == null) {
                synchronized(LocalDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                            LocalDatabase::class.java, "local.db")
                            .allowMainThreadQueries()
                            .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }

}