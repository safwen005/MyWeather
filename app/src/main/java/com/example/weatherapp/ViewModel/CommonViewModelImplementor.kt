package com.thinkit.smartyhome.ViewModel


import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.Utilities.Coroutines.MYIO
import com.example.weatherapp.Utilities.LocationCountResponse
import com.example.weatherapp.Utilities.LocationResponse
import com.example.weatherapp.data.Network.responses.WeatherResponse
import com.example.weatherapp.data.Repository.WeatherRepository
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class CommonViewModelImplementor() : ViewModel(), CommonViewModel {

    lateinit var weatherRepository: WeatherRepository

    lateinit var activity: AppCompatActivity


    fun Init(activity: AppCompatActivity) {
        if (!::weatherRepository.isInitialized) {
            this.activity = activity
            weatherRepository = WeatherRepository().also {
                it.InitValues(activity)
            }
        }
    }

    override fun GetLocation(): LiveData<LocationResponse>? {
        return weatherRepository.getLatLong(activity)
    }


    override
    fun First_Time(): Boolean? {
        return weatherRepository.First_Time()
    }


    override fun putKey(key: String, value: Any) {
        weatherRepository.putKey(key, value)
    }


    inline fun <reified type> getkey(key: String): type? {
        return weatherRepository.getkey<type>(key)
    }


    override fun SaveSettings(vararg list: Pair<String, Any>) {
        weatherRepository.SaveSettings(*list)
    }

    override fun GetLocationsCount(): LocationCountResponse {
        return weatherRepository.GetLocationsCount()
    }


    fun GetWeather(lat:Double,lon:Double): LiveData<WeatherResponse> {
        val WeatherResult = MutableLiveData<WeatherResponse>()
        MYIO{
            withContext(Main) {
                WeatherResult.value = async {
                    weatherRepository.GetWeather(lat,lon)

                }.await()
            }

        }
        return WeatherResult
    }

}