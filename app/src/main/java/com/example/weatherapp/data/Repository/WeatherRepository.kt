package com.example.weatherapp.data.Repository

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weatherapp.Utilities.Constants.api_key
import com.example.weatherapp.Utilities.Constants.days
import com.example.weatherapp.Utilities.Constants.hours
import com.example.weatherapp.Utilities.LocationResponse
import com.example.weatherapp.Utilities.WeathersList
import com.example.weatherapp.data.Network.Get_Location
import com.example.weatherapp.data.Network.Interceptor
import com.example.weatherapp.data.Network.MyApiCall
import com.example.weatherapp.data.Network.responses.Daily.Daily
import com.example.weatherapp.data.Network.responses.Hourly.Hourly
import com.example.weatherapp.data.Network.responses.WeatherAll
import com.example.weatherapp.data.Network.responses.WeatherResponse
import com.example.weatherapp.data.Network.responses.myData
import com.example.weatherapp.data.db.Weatherdatabase
import com.example.weatherapp.data.db.entities.WeatherModel
import com.google.gson.Gson
import java.lang.Exception


class WeatherRepository() {


    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    lateinit var getLocation: Get_Location
    lateinit var weatherdatabase: Weatherdatabase
    lateinit var activity: Activity
    lateinit var gson: Gson

    @SuppressLint("CommitPrefEdits")
    fun InitValues(activity: Activity) {
        if (!::sharedPreferences.isInitialized) {
            this.activity = activity
            gson = Gson()
            sharedPreferences =
                activity.application.getSharedPreferences("Cache", Context.MODE_PRIVATE)
            editor = sharedPreferences.edit()
            getLocation = Get_Location(activity)
            weatherdatabase = Weatherdatabase.getInstance(activity.application)
        }
    }


    fun First_Time(): Boolean? {
        sharedPreferences.apply {
            getkey<Boolean>("first_time")?.let { result ->
                if (result) {
                    putKey("first_time", true)
                    return true
                }
                return false
            }
            return null
        }
    }


    fun putKey(key: String, value: Any) {
        editor.apply {
            when (value) {
                is Int -> putInt(key, value)
                is String -> putString(key, value)
                is Boolean -> putBoolean(key, value)
            }
            apply()
        }
    }

    inline fun <reified type> getkey(key: String): type? {
        var result: type? = null
        sharedPreferences.apply {
            when (type::class) {
                Int::class -> result = getInt(key, -1) as type
                String::class -> result = getString(key, "") as type
                Boolean::class -> result = getBoolean(key, false) as type
            }
        }

        return result
    }


    fun SaveSettings(vararg list: Pair<String, Any>) {
        list.forEach { Item ->
            Item.apply {
                editor.apply {
                    putKey(first, second)
                }
            }
        }
    }


    fun getLatLong(lifecycleOwner: LifecycleOwner): LiveData<LocationResponse> {
        val result = MutableLiveData<LocationResponse>()
        getLocation.result.observe(lifecycleOwner) {
            result.value = it
        }
        getLocation.start()
        return result
    }

    suspend fun GetWeatherHourly(lat: Double, lon: Double, units: Char = 'M'): List<Hourly> {
        return MyApiCall(Interceptor(activity)).Hourly(
            lat,
            lon,
            hours,
            units,
            api_key
        ).data
    }

    suspend fun GetWeatherCurrently(lat: Double, lon: Double, units: Char = 'M'): WeatherResponse {
        return MyApiCall(Interceptor(activity)).Current(
            lat,
            lon,
            units,
            api_key
        )
    }

    suspend fun GetWeatherDaily(lat: Double, lon: Double, units: Char = 'M'): List<Daily> {
        return MyApiCall(Interceptor(activity)).Daily(
            lat,
            lon,
            days,
            units,
            api_key
        ).data
    }

    fun SaveLastSelectedWeather(weatherAll: myData) {
        val json = gson.toJson(weatherAll);
        editor.putString("last_selected_weather", json)
        editor.apply()
    }

    fun LoadLastSelectedWeather(): myData? {
        val json = sharedPreferences.getString("last_selected_weather", null)
        return gson.fromJson(json, myData::class.java)
    }

    suspend fun SaveWeather(weatherAll: myData) {
        weatherdatabase.RoomDao().InsertWeather(WeatherModel(weatherAll = weatherAll))
    }

    fun loadWeathers(): WeathersList {
        try {
            return WeathersList(weatherdatabase.RoomDao().loadWeathers(), null)
        } catch (exception: Exception) {
            return WeathersList(null, exception)
        }
    }

}