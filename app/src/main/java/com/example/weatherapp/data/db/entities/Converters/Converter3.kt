package com.example.weatherapp.data.db.entities.Converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class Converter3 {

    @TypeConverter
    fun from(value: Array<HashMap<String, String>>) = Json.encodeToString(value)

    @TypeConverter
    fun to(value: String) = Json.decodeFromString<Array<HashMap<String, String>>>(value)

}