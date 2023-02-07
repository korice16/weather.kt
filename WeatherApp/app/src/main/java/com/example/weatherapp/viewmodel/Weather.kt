package com.example.weatherapp.viewmodel

import com.example.weatherapp.model.datatype.*
import com.example.weatherapp.model.enumeration.*
import org.json.JSONArray
import org.json.JSONObject

class Weather{
    private var city:String
    private val weatherConditions: ArrayList<WeatherConditionType>
    private var temp:TempType
    private var pressure:PressureType
    private var humidity:HumidityType
    private var wind:WindType
    private var clouds:CloudType
    private var sunTime:SunTimeType

    constructor(city:String, weatherConditions:ArrayList<WeatherConditionType>,  temp:TempType, pressure:PressureType, humidity: HumidityType, wind: WindType,clouds: CloudType ,sunTime:SunTimeType){
        this.city=city
        this.weatherConditions=weatherConditions
        this.temp=temp
        this.pressure=pressure
        this.humidity=humidity
        this.wind=wind
        this.clouds=clouds
        this.sunTime=sunTime
    }

    //static method

    companion object{
        /**
         * Creer une classe Weatehr et el renvoie
         *
         * return Weather
         */
        fun jsonWeather(response:JSONObject):Weather{
            val jsonObjectWeather=response.getJSONArray("weather")
            val jsonObjectMain=response.getJSONObject("main")
            val jsonObjectWind=response.getJSONObject("wind")
            val jsonObjectClouds=response.getJSONObject("clouds")
            val jsonObjectSys=response.getJSONObject("sys")

            val ruf=RepositoryUsefulFun

            val city=response.getString("name")
            val weatherCond= creatWeatherC(jsonObjectWeather)

            val temp_act=ruf.kelvinToCelsius(jsonObjectMain.getDouble("temp"))
            val temp_feels=ruf.kelvinToCelsius(jsonObjectMain.getDouble("feels_like"))
            val temp_min=ruf.kelvinToCelsius(jsonObjectMain.getDouble("temp_min"))
            val temp_max=ruf.kelvinToCelsius(jsonObjectMain.getDouble("temp_max"))

            //val temp=TempType(jsonObjectMain.getDouble("temp"),jsonObjectMain.getDouble("feels_like"),jsonObjectMain.getDouble("temp_min"),jsonObjectMain.getDouble("temp_max"),TemparatureUnit.CELSIUS)
            val temp=TempType(temp_act,temp_feels,temp_min,temp_max,TemparatureUnit.CELSIUS)
            val pressure=PressureType(jsonObjectMain.getInt("pressure"),PressureUnit.HECTOPASCAL)
            val humidity=HumidityType(jsonObjectMain.getInt("humidity"),HumidityUnit.PERCENT)

            val wind=WindType(ruf.meter_secToKilometer_hour(jsonObjectWind.getDouble("speed"),1), SpeedUnit.KILOMETER_HOUR,jsonObjectWind.getDouble("deg"), ruf.windDegreesToCompass(jsonObjectWind.getDouble("deg")))
            val clouds=CloudType(jsonObjectClouds.getInt("all"),CloudUnit.PERCENT)
            val suntime=SunTimeType(ruf.unixTimestampToDate(jsonObjectSys.getLong("sunrise")),ruf.unixTimestampToDate(jsonObjectSys.getLong("sunset")))

            val weather=Weather(city, weatherCond, temp, pressure, humidity, wind, clouds,suntime)
            return weather
        }

        /**
         * fonction qui permet de creer une ArrayList<WeatherConditionType> et le renvoie
         */
        private fun creatWeatherC(weatherJsonArray: JSONArray):ArrayList<WeatherConditionType>{
            val weatherConditions= ArrayList<WeatherConditionType>()
            for(i in 0 until weatherJsonArray.length()){
                val jsonObjectWeatherC=weatherJsonArray.getJSONObject(i)
                val weatherC=WeatherConditionType(jsonObjectWeatherC.getInt("id"),jsonObjectWeatherC.getString("main"),jsonObjectWeatherC.getString("description"),jsonObjectWeatherC.getString("icon"))
                weatherConditions.add(weatherC)
            }
            return weatherConditions
        }

        private fun kTc(){

        }
    }

    /**
     * getters
     */
    fun getCity():String{return city}
    fun getWeatherConditions():ArrayList<WeatherConditionType>{return this.weatherConditions}
    fun getTemp():TempType{return this.temp}
    fun getPressure():PressureType{return this.pressure}
    fun getHumidity():HumidityType{return this.humidity}
    fun getWind():WindType{return this.wind}
    fun getClouds():CloudType{return this.clouds}
    fun getSunTime():SunTimeType{return this.sunTime}
}