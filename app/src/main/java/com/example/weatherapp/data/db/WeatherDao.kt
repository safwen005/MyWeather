package com.example.weatherapp.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.weatherapp.data.db.entities.WeatherModel

@Dao
interface WeatherDao {

    @Insert
    suspend fun InsertRoom(roomsModel: WeatherModel)

    @Query("SELECT COUNT(id) FROM Weather_Table")
    fun GetLocationsCount(): LiveData<Int>

    /*
    @Query("SELECT COUNT(column) FROM table")
    fun getDataCount(): Int

     */
}