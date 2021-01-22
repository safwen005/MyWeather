package com.example.weatherapp.data.Network.responses

import com.example.weatherapp.data.Network.responses.Daily.Daily
import com.example.weatherapp.data.Network.responses.Hourly.Hourly

data class WeatherAll (var current:WeatherResponse?=null,var hourly:List<Hourly>?=null,var daily:List<Daily>?=null)