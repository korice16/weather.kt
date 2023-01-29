package com.example.weatherapp.model.datatype

import com.example.weatherapp.model.enumeration.PressureUnit

data class PressureType(private val pressure:Int, private val unit: PressureUnit){

    /**
     * Renvoie la pression en double
     */
    fun getPressure():Int{return this.pressure}

    /**
     * Renvoie l'unité de la pression
     */
    fun getUnit():PressureUnit{return this.unit}
}
