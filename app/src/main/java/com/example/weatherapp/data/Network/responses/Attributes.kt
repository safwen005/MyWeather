package com.example.weatherapp.data.Network.responses


import com.google.gson.annotations.SerializedName

data class Attributes(
    @SerializedName("body")
    val body: String,
    @SerializedName("created")
    val created: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("updated")
    val updated: String
)