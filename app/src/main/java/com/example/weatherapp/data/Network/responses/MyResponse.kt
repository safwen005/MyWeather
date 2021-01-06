package com.example.weatherapp.data.Network.responses


import com.google.gson.annotations.SerializedName

data class MyResponse(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("included")
    val included: List<Included>
)