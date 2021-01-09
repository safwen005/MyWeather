package com.example.weatherapp.data.Repository

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weatherapp.Utilities.LocationCountResponse
import com.example.weatherapp.Utilities.LocationResponse
import com.example.weatherapp.data.Network.Get_Location
import com.example.weatherapp.data.Network.Interceptor
import com.example.weatherapp.data.Network.MyApiCall
import com.example.weatherapp.data.Network.responses.WeatherResponse
import com.example.weatherapp.data.db.Weatherdatabase

class WeatherRepository() {


    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    lateinit var getLocation: Get_Location
    lateinit var weatherdatabase: Weatherdatabase
    lateinit var activity: Activity

    @SuppressLint("CommitPrefEdits")
    fun InitValues(activity: Activity) {
        if (!::sharedPreferences.isInitialized) {
            this.activity = activity
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

    fun GetLocationsCount(): LocationCountResponse {
        try {
            return LocationCountResponse(weatherdatabase.RoomDao().GetLocationsCount(), null)
        } catch (exception: Exception) {
            return LocationCountResponse(null, exception)
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

    suspend fun GetWeather(lat:Double,lon:Double): WeatherResponse {
        return MyApiCall(Interceptor(activity)).request(
            lat,
            lon,
            "c531487b86964e05bc82f5d80e1b3c34"
        )
    }


}