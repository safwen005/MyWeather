package com.thinkit.smartyhome.ViewModel


import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.Utilities.Coroutines.MYIO
import com.example.weatherapp.Utilities.LocationResponse
import com.example.weatherapp.Utilities.WeathersList
import com.example.weatherapp.data.Network.responses.WeatherAll
import com.example.weatherapp.data.Network.responses.WeatherResponse
import com.example.weatherapp.data.Network.responses.myData
import com.example.weatherapp.data.Repository.WeatherRepository
import com.example.weatherapp.data.db.entities.WeatherModel
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import java.lang.Exception

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


    fun getWeather(lat: Double, lon: Double): LiveData<WeatherAll> {
        val weatherAll = WeatherAll()
        val WeatherResult = MutableLiveData<WeatherAll>()
        MYIO {
            withContext(Main) {
                weatherAll.current = async {
                    weatherRepository.GetWeatherCurrently(lat, lon)
                }.await()

                weatherAll.hourly = async {
                    weatherRepository.GetWeatherHourly(lat, lon)
                }.await()
                weatherAll.daily = async {
                    weatherRepository.GetWeatherDaily(lat, lon)
                }.await()
                WeatherResult.value = weatherAll
            }
        }
        return WeatherResult
    }

    fun SaveLastSelectedWeather(weatherAll: WeatherModel) {
        weatherRepository.SaveLastSelectedWeather(weatherAll)
    }

    fun LoadLastSelectedWeather(): WeatherModel? {
        return weatherRepository.LoadLastSelectedWeather()
    }

    fun saveWeather(myData: myData): Exception? {
        var exception: Exception? = null
        MYIO {
            try {
                weatherRepository.SaveWeather(myData)

            } catch (e: Exception) {
                exception = e
            }
        }
        return exception
    }

    fun updateWeather(myData: myData, index: Int) {
        MYIO {
            weatherRepository.updateWeather(myData, index)
        }
    }

    fun loadWeathers(): WeathersList {
        return weatherRepository.loadWeathers()
    }


}