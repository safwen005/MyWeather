package com.example.weatherapp.data.Network.responses


import com.google.gson.annotations.SerializedName

data class Author(
    @SerializedName("data")
    val `data`: DataX
)