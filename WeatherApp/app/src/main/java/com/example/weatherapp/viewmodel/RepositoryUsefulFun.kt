package com.example.weatherapp.viewmodel

import com.example.weatherapp.model.enumeration.WindDirection
import java.util.Calendar
import java.util.Date

object RepositoryUsefulFun {

    /**
     * Transforme un unixTimestamp utc+0 en Date, en fonction de utc
     */
    fun unixTimestampToDate(unixTimestamp:Long):Date{
        val calendar= Calendar.getInstance().apply {
            timeInMillis= unixTimestamp * 1000
        }
        return calendar.time
    }

    /**
     * Convertie la temperature Kelvin en Celius
     */
    fun kelvinToCelsius(kelvin:Double):Double{
        return Math.round(kelvin-273.15).toDouble()
    }

    /**
     * Convertie les degrees de la direction du vent en coordonnes bussole
     */
    fun windDegreesToCompass(windDegrees: Double):WindDirection{
        val limitation:Double= (360.00/WindDirection.values().size)
        val index=Math.round(windDegrees/limitation).toInt()
        return WindDirection.values()[index % WindDirection.values().size]
    }

    /**
     * Convertie les m/s en km/h
     */
    fun meter_secToKilometer_hour(m_s:Double,decimalPlaces:Int?):Double{
        val km_h= m_s*3.60
        //val roundedNumber=("%.1f".format(km_h))toDouble()//arrondie à une virgule apres
        if(decimalPlaces!=null){
            //Cette méthode multiplie le nombre par pow(10,n) , l'arrondit au nombre entier le plus proche et divise par pow(10,n) pour obtenir l'arrondi à un chiffre après la virgule.n nmb apres virgule

            val nb_div=Math.pow(10.0, decimalPlaces.toDouble())
            val roundedNumber=Math.round(km_h * nb_div ) / nb_div
            return roundedNumber
        }else{
            return  km_h
        }

    }
}