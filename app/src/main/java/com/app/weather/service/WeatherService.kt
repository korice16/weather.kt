package com.app.weather.service

import com.app.weather.model.api.WeatherResponse
import retrofit2.http.*


interface WeatherService {
   @GET("weather")
   suspend fun getWeather(@Query("q") city: String, @Query("appid") appId: String): WeatherResponse
}