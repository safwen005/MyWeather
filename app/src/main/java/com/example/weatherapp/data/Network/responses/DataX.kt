package com.example.weatherapp.data.Network.responses


import com.google.gson.annotations.SerializedName

data class DataX(
    @SerializedName("id")
    val id: String,
    @SerializedName("type")
    val type: String
)