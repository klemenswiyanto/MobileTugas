package com.github.klemenswiyanto.gymcomp.data

import android.content.Context
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Gym::class], version = 1)
abstract class GymDatabase : RoomDatabase() {

    abstract fun doesDao(): GymDao


    companion object {
        private var instance: GymDatabase? = null

        fun getInstance(context: Context): GymDatabase? {
            if (instance == null) {
                synchronized(GymDatabase::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        GymDatabase::class.java, "does_database"
                    )
                        .fallbackToDestructiveMigration() // when version increments, it migrates (deletes db and creates new) - else it crashes
                        .addCallback(roomCallback)
                        .build()
                }
            }
            return instance
        }

        private val roomCallback = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                PopulateDbAsyncTask(instance).execute()
            }
        }
    }

    class PopulateDbAsyncTask(db: GymDatabase?) : AsyncTask<Unit, Unit, Unit>() {
        private val doesDao = db?.doesDao()

        override fun doInBackground(vararg p0: Unit?) {
            doesDao?.insert(Gym("kegiatan", "1", "5", "10 Oct 2019"))
            doesDao?.insert(Gym("title 2", "2", "5", "13 Oct 2019"))
            doesDao?.insert(Gym("title 3", "3", "5", "21 Oct 2019"))
        }
    }

}