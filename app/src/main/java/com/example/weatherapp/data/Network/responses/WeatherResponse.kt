package com.example.weatherapp.data.Network.responses


import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @SerializedName("count")
    val count: Int,
    @SerializedName("data")
    val `data`: List<Data>
)