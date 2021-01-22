package com.example.weatherapp.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.weatherapp.data.Network.responses.WeatherAll
import com.example.weatherapp.data.Network.responses.myData

@Entity(tableName = "Weather_Table")
data class WeatherModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @Embedded
    val weatherAll: myData
)