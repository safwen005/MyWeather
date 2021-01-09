package com.example.weatherapp.Utilities

import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weatherapp.data.Repository.WeatherRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import retrofit2.Response

object Coroutines {


    fun Main(duration: Long, other: () -> Unit) {
        CoroutineScope(Main).launch {
            delay(duration)
            other()
        }
    }

    fun MYIO(stuff: suspend () -> Unit) {
        CoroutineScope(IO).launch {
            stuff()
        }
    }


}

