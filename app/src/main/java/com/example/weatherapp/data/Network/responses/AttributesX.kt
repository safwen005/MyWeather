package com.example.weatherapp.data.Network.responses


import com.google.gson.annotations.SerializedName

data class AttributesX(
    @SerializedName("age")
    val age: Int,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("name")
    val name: String
)