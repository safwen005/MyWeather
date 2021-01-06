package com.example.weatherapp.data.Network

import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface MyApiCall {



    // CALLS

    @FormUrlEncoded
    @POST("qsd")
    suspend fun request(): Response<String>



    companion object {
        operator fun invoke(interceptor: Interceptor):MyApiCall{
            val okHttpClient=OkHttpClient.Builder().addInterceptor(interceptor).build()
            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("qsdsqdsqdsq")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MyApiCall::class.java)
        }
    }




}