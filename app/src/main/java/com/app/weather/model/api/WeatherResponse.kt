package com.app.weather.model.api

import com.squareup.moshi.Json


data class WeatherResponse (
    @Json(name = "main")
    val basicData: BasicWeatherData,
)

data class BasicWeatherData (
    @Json(name ="temp")
    val temperature: Double,
    @Json(name = "pressure")
    val pressure: Int,
    @Json(name = "humidity")
    val humidity: Int
)