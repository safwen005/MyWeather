package com.example.weatherapp.data.Network

import com.example.weatherapp.Utilities.Constants.url
import com.example.weatherapp.data.Network.responses.Daily.Daily
import com.example.weatherapp.data.Network.responses.Hourly.Hourly
import com.example.weatherapp.data.Network.responses.WeatherResponse
import com.example.weatherapp.data.Network.responses.main
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface MyApiCall {

    @GET("current")
    suspend fun Current(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("units") units: Char,
        @Query("key") key: String
    ): WeatherResponse


    @GET("forecast/hourly")
    suspend fun Hourly(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("hours") hour: Int,
        @Query("units") units: Char,
        @Query("key") key: String
    ): main<Hourly>

    @GET("forecast/daily")
    suspend fun Daily(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("days") days: Int,
        @Query("units") units: Char,
        @Query("key") key: String
    ): main<Daily>

    companion object {
        operator fun invoke(interceptor: Interceptor): MyApiCall {
            val okHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()
            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MyApiCall::class.java)
        }
    }


}