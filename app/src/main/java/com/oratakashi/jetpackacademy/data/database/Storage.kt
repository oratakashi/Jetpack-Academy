package com.oratakashi.jetpackacademy.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.oratakashi.jetpackacademy.BuildConfig
import com.oratakashi.jetpackacademy.data.database.dao.MovieDao
import com.oratakashi.jetpackacademy.data.database.dao.TvDao
import com.oratakashi.jetpackacademy.data.model.movie.DataMovie
import com.oratakashi.jetpackacademy.data.model.tv.DataTv

@Database(
    entities = [
        DataMovie::class,
        DataTv::class
    ],
    version = BuildConfig.VERSION_CODE
)
abstract class Storage : RoomDatabase() {

    abstract fun movie() : MovieDao
    abstract fun tv() : TvDao

    companion object {

        @Volatile private var instance : Storage? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            Storage::class.java,
            BuildConfig.APPLICATION_ID
        )
            .fallbackToDestructiveMigration()
            .build()

    }
}