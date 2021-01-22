package com.example.weatherapp.data.Network.responses

import com.google.gson.annotations.SerializedName

data class main<type>(
    @SerializedName("data")
    val `data`: List<type>
)