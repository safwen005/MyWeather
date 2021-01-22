package com.example.weatherapp.data.Network.responses.Hourly


import com.google.gson.annotations.SerializedName

data class Hourly(
    @SerializedName("app_temp")
    val appTemp: Double,
    @SerializedName("clouds")
    val clouds: Double,
    @SerializedName("clouds_hi")
    val cloudsHi: Double,
    @SerializedName("clouds_low")
    val cloudsLow: Double,
    @SerializedName("clouds_mid")
    val cloudsMid: Double,
    @SerializedName("datetime")
    var datetime: String,
    @SerializedName("dewpt")
    val dewpt: Double,
    @SerializedName("dhi")
    val dhi: Double,
    @SerializedName("dni")
    val dni: Double,
    @SerializedName("ghi")
    val ghi: Double,
    @SerializedName("ozone")
    val ozone: Double,
    @SerializedName("pod")
    var Cityname: String,
    @SerializedName("pop")
    val pop: Double,
    @SerializedName("precip")
    val precip: Double,
    @SerializedName("pres")
    val pres: Double,
    @SerializedName("rh")
    val rh: Double,
    @SerializedName("slp")
    val slp: Double,
    @SerializedName("snow")
    val snow: Double,
    @SerializedName("snow_depth")
    val snowDepth: Double,
    @SerializedName("solar_rad")
    val solarRad: Double,
    @SerializedName("temp")
    val temp: Double,
    @SerializedName("timestamp_local")
    val timestampLocal: String,
    @SerializedName("timestamp_utc")
    var Time: String,
    @SerializedName("ts")
    val ts: Double,
    @SerializedName("uv")
    val uv: Double,
    @SerializedName("vis")
    val vis: Double,
    @SerializedName("weather")
    val weather: Weather,
    @SerializedName("wind_cdir")
    var Sunrise: String,
    @SerializedName("wind_cdir_full")
    var Sunset: String,
    @SerializedName("wind_dir")
    val windDir: Double,
    @SerializedName("wind_gust_spd")
    val windGustSpd: Double,
    @SerializedName("wind_spd")
    val windSpd: Double
)