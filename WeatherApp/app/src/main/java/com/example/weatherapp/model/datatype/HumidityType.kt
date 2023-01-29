package com.example.weatherapp.model.datatype

import com.example.weatherapp.model.enumeration.HumidityUnit

data class HumidityType(private val value: Int, private val unit: HumidityUnit){

    /**
     * Renvoie la valeur de l'humidite en Double
     */
    fun getHumidity():Int{return this.value}

    /**
     * Renvoie de le type de l'humidite
     */
    fun getUnit():HumidityUnit{return this.unit}
}
