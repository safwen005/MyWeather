package com.example.weatherapp.data.Network.responses.Hourly


import com.google.gson.annotations.SerializedName

data class Weather(
    @SerializedName("code")
    val code: Int,
    @SerializedName("description")
    val description: String,
    @SerializedName("icon")
    val icon: String
)