package com.example.weatherapp.model.datatype

import com.example.weatherapp.viewmodel.RepositoryUsefulFun
import java.util.Date

data class SunTimeType(private val sunrise: Date,private val sunset: Date){

    fun getSunriseDate(): Date{
        return this.sunrise
    }

    fun getSunsetDate(): Date{
        return this.sunset
    }

}
