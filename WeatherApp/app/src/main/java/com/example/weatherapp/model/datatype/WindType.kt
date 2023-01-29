package com.example.weatherapp.model.datatype

import com.example.weatherapp.model.enumeration.SpeedUnit
import com.example.weatherapp.model.enumeration.WindDirection

data class WindType(private val speed: Double, private val speedUnit: SpeedUnit,  private val deg: Double, private val direction: WindDirection){


    /**
     * Renvoie la vitesse du vent
     */
    fun getSpeed():Double{
        return this.speed
    }

    /**
     * renvoie la direction du vent suivant les coordonnes de la boussole
     */
    fun getDirection():WindDirection{
        return this.direction
    }

    /**
     * renvoie la direction du vent suivant les coordonnes de la boussole
     */
    fun getSpeedUnit():SpeedUnit{
        return this.speedUnit
    }
}
