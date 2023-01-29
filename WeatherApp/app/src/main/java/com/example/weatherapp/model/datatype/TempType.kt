package com.example.weatherapp.model.datatype

import com.example.weatherapp.model.enumeration.TemparatureUnit
import com.example.weatherapp.viewmodel.RepositoryUsefulFun

data class TempType(
    private var temp: Double,
    private var feels_like:Double,
    private var temp_min: Double,
    private var temp_max: Double,
    private var temp_unit: TemparatureUnit
){


    /**
     * Renvoie la temperature actuelle
     */
    fun getTemp():Double{
        return this.temp
    }

    /**
     * Renvoie La temperature ressente par l'homme par rapport au temps
     */
    fun getTempFeels_like():Double{
        return this.feels_like
    }

    /**
     * Renvoie La temperature minimum
     */
    fun getTemp_min():Double{
        return this.temp_min
    }

    /**
     * Renvoie la temperature maximum
     */
    fun getTemp_max():Double{
        return this.temp_max
    }

    /**
     * Renvoie l'unite de le temperature utilise
     */
    fun getTemp_unit():TemparatureUnit{
        return this.temp_unit
    }
}
