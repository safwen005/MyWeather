package com.example.weatherapp.data.db

import android.content.Context
import androidx.databinding.adapters.Converters
import androidx.room.*
import com.example.weatherapp.data.db.entities.Converters.Converter
import com.example.weatherapp.data.db.entities.Converters.Converter2
import com.example.weatherapp.data.db.entities.Converters.Converter3
import com.example.weatherapp.data.db.entities.WeatherModel


@Database(
    entities = [WeatherModel::class],
    version = Weatherdatabase.DatabaseVersion
)
@TypeConverters(Converter::class, Converter2::class, Converter3::class)
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
            ).fallbackToDestructiveMigration().build().also { instance = it }

        }

    }

}