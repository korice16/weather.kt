package com.example.weatherapp.viewmodel

import android.content.Context
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.weatherapp.model.datatype.*
import com.example.weatherapp.model.enumeration.TemparatureUnit
import com.example.weatherapp.model.enumeration.WindDirection
import org.json.JSONObject

class Weather{
    private var city:String //creer une classe
    private var suntime:SunTimeType
    private var temp:TempType
    private var pressure:PressureType //creer
    private var humidity:HumidityType
    private var wind:WindType//wind_speed and windspe_direction
    private var weather: ConditionType//ArrayList mapOf()) ou dataclass avec tout les informations


    constructor(city:String, suntime:SunTimeType, temp:TempType, pressure:PressureType, humidity: HumidityType, wind: WindType, weather: ConditionType){
        this.city=city
        this.suntime=suntime
        this.temp=temp
        this.pressure=pressure
        this.humidity=humidity
        this.wind=wind
        this.weather=weather
    }

    //static method
    companion object{
        fun jsonWeather(response:JSONObject):Weather{
            val jsonObjectWeather=response.getJSONArray("weather").getJSONObject(0)
            val jsonObjectMain=response.getJSONObject("main")
            val jsonObjectWind=response.getJSONObject("wind")
            val jsonObjectSys=response.getJSONObject("sys")

            val suntime=SunTimeType(jsonObjectSys.getInt("sunrise"),jsonObjectSys.getInt("sunset"))
            val temp=TempType(jsonObjectMain.getDouble("temp"),jsonObjectMain.getDouble("feels_like"),jsonObjectMain.getDouble("temp_min"),jsonObjectMain.getDouble("temp_max"),TemparatureUnit.CELSIUS)
            val wind=WindType(jsonObjectWind.getDouble("speed"),jsonObjectWind.getDouble("deg"))
            val weatherC=ConditionType(jsonObjectWeather.getInt("id"),jsonObjectWeather.getString("main"),jsonObjectWeather.getString("description"),jsonObjectWeather.getString("icon"))
            val pressure=PressureType(jsonObjectMain.getDouble("pressure"))
            val humidity=HumidityType(jsonObjectMain.getDouble("pressure"))
            val city=response.getString("name")

            val weather=Weather(city, suntime, temp, pressure, humidity, wind, weatherC)
            return weather
        }

        fun cityWeather(context: Context,cityName:String):Weather{
            lateinit var weather: Weather
            val urlAPI ="https://api.openweathermap.org/data/2.5/weather"
            val appid ="abb8e45cee0476bb9a364e0404835ad9"
            val url=urlAPI+"?q="+cityName+"&appid="+appid
            val queue= Volley.newRequestQueue(context)
            val jsonObjectRequest= JsonObjectRequest(
                Request.Method.GET,url,null,
                Response.Listener { response ->
                    //Log.d("ManageCities","test")
                    //Log.d("ManageCitiesAll",response.toString())
                    //textView.setText("reussi")
                    //weather= jsonWeather(response)
                    //store(response.getString("name"))
                    //addW(response)
                    //Toast.makeText(this, weather.getVille(), Toast.LENGTH_LONG).show()
                    weather= jsonWeather(response)
                }, Response.ErrorListener {
                    //Toast.makeText(this, "error", Toast.LENGTH_LONG).show()
                }

            )
            queue.add(jsonObjectRequest)
            return weather
        }
    }

    fun getVille():String{return city}
    fun getTemp():TempType{return this.temp}
    fun getSunTime():SunTimeType{return this.suntime}
    fun getWind():WindType{return this.wind}
    fun getWeather():ConditionType{return this.weather}
}