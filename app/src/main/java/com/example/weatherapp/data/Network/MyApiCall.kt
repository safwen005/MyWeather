package com.example.weatherapp.data.Network

import com.example.weatherapp.Utilities.Constants.weather_api
import com.example.weatherapp.data.Network.responses.WeatherResponse
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface MyApiCall {


    // CALLS

    @GET("current")
    suspend fun request(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("key") key: String,
    ): WeatherResponse


    companion object {
        operator fun invoke(interceptor: Interceptor): MyApiCall {
            val okHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()
            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(weather_api)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MyApiCall::class.java)
        }
    }


}