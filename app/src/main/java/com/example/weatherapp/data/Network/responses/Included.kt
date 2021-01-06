package com.example.weatherapp.data.Network.responses


import com.google.gson.annotations.SerializedName

data class Included(
    @SerializedName("attributes")
    val attributes: AttributesX,
    @SerializedName("id")
    val id: String,
    @SerializedName("type")
    val type: String
)