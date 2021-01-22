package com.example.weatherapp.data.Network.responses

import androidx.room.TypeConverters
import com.example.weatherapp.data.db.entities.Converters.Converter2

class myData(
    @TypeConverters(Converter2::class)
    val current: HashMap<String, String>,
    val hourly: Array<HashMap<String, String>?>,
    val daily: Array<HashMap<String, String>?>
)