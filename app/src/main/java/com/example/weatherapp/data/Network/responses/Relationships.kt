package com.example.weatherapp.data.Network.responses


import com.google.gson.annotations.SerializedName

data class Relationships(
    @SerializedName("author")
    val author: Author
)