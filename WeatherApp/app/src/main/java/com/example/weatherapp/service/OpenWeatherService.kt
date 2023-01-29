package com.example.weatherapp.service

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.weatherapp.viewmodel.Weather

class OpenWeatherService {
    val urlAPI ="https://api.openweathermap.org/data/2.5/weather"
    private val appid ="abb8e45cee0476bb9a364e0404835ad9"
    //https://api.openweathermap.org/data/2.5/weather?q=paris&lang=fr&appid=abb8e45cee0476bb9a364e0404835ad9
    companion object {
        val urlAPI1 ="https://api.openweathermap.org/data/2.5/weather"
        val appid1 ="abb8e45cee0476bb9a364e0404835ad9"
        fun getWeather1(context: Context, city: String, callback: (Weather?) -> Unit) {
            val url1 = urlAPI1 + "?q=" + city + "&lang=fr&appid=" + appid1
            val queue1 = Volley.newRequestQueue(context)
            val jsonObjectRequest1 = JsonObjectRequest(
                Request.Method.GET, url1, null,
                Response.Listener { response ->

                    //setValue(response)
                    val data1 = Weather.jsonWeather(response)
                    //Toast.makeText(this, response.toString(), Toast.LENGTH_LONG).show()
                    callback(data1)
                    Log.d("OpenWeatherService", "reussite getWeather")
                }, Response.ErrorListener {
                    //Toast.makeText(this, "error", Toast.LENGTH_LONG).show()
                    Log.d("OpenWeatherService", "non reussite getWeather")
                    callback(null)
                }

            )
            queue1.add(jsonObjectRequest1)
        }
    }

    fun getWeather(context: Context, city: String, callback: (Weather?) -> Unit) {
        val url = urlAPI1 + "?q=" + city + "&appid=" + appid1
        val queue = Volley.newRequestQueue(context)
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->

                //setValue(response)
                val data = Weather.jsonWeather(response)
                //Toast.makeText(this, response.toString(), Toast.LENGTH_LONG).show()
                callback(data)
                Log.d("OpenWeatherService", "reussite getWeather")
            }, Response.ErrorListener {
                //Toast.makeText(this, "error", Toast.LENGTH_LONG).show()
                Log.d("OpenWeatherService", "reussite getWeather")
                callback(null)
            }

        )
    }

}