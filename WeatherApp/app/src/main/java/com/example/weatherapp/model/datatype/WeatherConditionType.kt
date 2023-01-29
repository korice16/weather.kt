package com.example.weatherapp.model.datatype


data class WeatherConditionType(
    val id:Int,
    val main:String,
    private val description:String,
    val icon:String
){

    fun getDescription():String{return this.description}
}
