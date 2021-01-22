package com.thinkit.smartyhome.ViewModel

import androidx.lifecycle.LiveData
import com.example.weatherapp.Utilities.LocationResponse

interface CommonViewModel {


    fun First_Time(): Boolean?
    fun SaveSettings(vararg list:Pair<String,Any>)
    fun putKey(key:String,value:Any)
    fun GetLocation():LiveData<LocationResponse>?
}