package com.example.weatherapp.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Weather_Table")
data class WeatherModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
)