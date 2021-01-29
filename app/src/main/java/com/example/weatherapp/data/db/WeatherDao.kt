package com.example.weatherapp.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.weatherapp.data.Network.responses.myData
import com.example.weatherapp.data.db.entities.WeatherModel

@Dao
interface WeatherDao {

    @Insert
    suspend fun InsertWeather(weatherModel: WeatherModel)

    @Query("SELECT * from weather_table")
    fun loadWeathers(): LiveData<List<WeatherModel>>

    @Query("UPDATE Weather_Table SET weatherAll= :myData WHERE `id` = :index")
    fun updateWeather(myData: myData, index: Int): Int


}