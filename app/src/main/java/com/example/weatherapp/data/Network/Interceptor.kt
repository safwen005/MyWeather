package com.example.weatherapp.data.Network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.example.weatherapp.Utilities.isInternetAvailable
import okhttp3.Interceptor
import okhttp3.Response
import java.lang.Exception

class Interceptor(val context: Context?) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        if (!isInternetAvailable(context?.applicationContext)){
            throw Exception("No Internet")
        }
        return chain.proceed(chain.request())

    }

}