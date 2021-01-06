package com.thinkit.smartyhome.ViewModel

import androidx.lifecycle.LiveData
import com.example.weatherapp.Utilities.LocationCountResponse
import com.example.weatherapp.Utilities.LocationResponse
import java.lang.Exception

interface CommonViewModel {


    fun First_Time(): Boolean?
    fun SaveSettings(vararg list:Pair<String,Any>)
    fun putKey(key:String,value:Any)
    fun GetWeather():LiveData<LocationResponse>?
    fun GetLocationsCount(): LocationCountResponse
}