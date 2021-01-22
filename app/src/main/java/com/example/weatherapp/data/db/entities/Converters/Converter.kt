package com.example.weatherapp.data.db.entities.Converters

import androidx.room.TypeConverter
import com.example.weatherapp.data.Network.responses.WeatherAll
import com.example.weatherapp.data.Network.responses.myData
import com.google.gson.Gson

class Converter {

    @TypeConverter
    fun fromSource(weatherAll: myData): String = Gson().toJson(weatherAll)

    @TypeConverter
    fun toSource(source: String): myData = Gson().fromJson(source, myData::class.java)

}