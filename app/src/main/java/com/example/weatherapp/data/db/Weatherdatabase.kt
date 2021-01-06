package com.example.weatherapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.weatherapp.data.db.entities.WeatherModel


@Database(
    entities = [WeatherModel::class],
    version = Weatherdatabase.DatabaseVersion
)
abstract class Weatherdatabase : RoomDatabase() {

    abstract fun RoomDao(): WeatherDao

    companion object {
        const val DatabaseVersion = 2
        var instance: Weatherdatabase? = null

        fun getInstance(context: Context): Weatherdatabase {
            return instance ?: Room.databaseBuilder(
                context.applicationContext,
                Weatherdatabase::class.java,
                "Weather_Database"
            ).fallbackToDestructiveMigration().build().also { instance=it }

        }

    }

}