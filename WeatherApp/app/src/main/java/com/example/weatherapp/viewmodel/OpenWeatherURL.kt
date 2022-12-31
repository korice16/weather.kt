package com.example.weatherapp.viewmodel

import android.content.Context
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley

object OpenWeatherURL {
    public val urlAPI ="https://api.openweathermap.org/data/2.5/weather"
    public val appid ="abb8e45cee0476bb9a364e0404835ad9"

    fun cityWeather(context: Context, cityName:String):Weather{
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
                weather= Weather.jsonWeather(response)
            }, Response.ErrorListener {
                //Toast.makeText(this, "error", Toast.LENGTH_LONG).show()
            }

        )
        queue.add(jsonObjectRequest)
        return weather
    }
}