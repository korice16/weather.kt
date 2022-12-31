package com.example.weatherapp.model.datatype

import com.example.weatherapp.model.enumeration.TemparatureUnit

data class TempType(
    private var temp: Double,
    private var feels_like:Double,
    private var temp_min: Double,
    private var temp_max: Double,
    private var temp_unit: TemparatureUnit
){

}
