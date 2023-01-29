package com.example.weatherapp.model.datatype

import com.example.weatherapp.model.enumeration.CloudUnit

data class CloudType(private val cloudiness: Int, private val unit: CloudUnit){

    /**
     * Renvoie quantite de la nebulosite en Integer
     */
    fun getCloudiness():Int{return this.cloudiness}

    /**
     * Renvoie l'unite de la nebulosite
     */
    fun getUnit():CloudUnit{return this.unit}
}
