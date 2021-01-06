package com.example.weatherapp.Utilities

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Response

object Coroutines{

    fun coro(stuff:suspend (()->Response<String>)):LiveData<String>{
        val result=MutableLiveData<String>()
        CoroutineScope(Main).launch {
            val job=async {
                stuff
            }.await()
            result.value=job().message()
        }
        return result
    }

    fun Main(duration:Long,other:()->Unit){
        CoroutineScope(Main).launch {
            delay(duration)
            other()
        }
    }
}

