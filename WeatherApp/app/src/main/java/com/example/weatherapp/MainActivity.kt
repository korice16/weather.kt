package com.example.weatherapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request

//import com.android.volley.RequestQueu=
//import com.and
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.weatherapp.viewmodel.OpenWeatherURL
import com.example.weatherapp.viewmodel.Weather
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
  public val baseAddr ="https://api.openweathermap.org/data/2.5/weather"

    private lateinit var queue:RequestQueue
    lateinit var weather: Weather
    private lateinit var textView:TextView
    private lateinit var temp:TextView
    private lateinit var descipt:TextView
    private lateinit var speed:TextView
    private lateinit var sunrise:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textView=findViewById(R.id.textViewCity)
        temp=findViewById(R.id.temp)
        descipt=findViewById(R.id.description)
        speed=findViewById(R.id.speed)
        sunrise=findViewById(R.id.Sunrise)

         request("Paris")
        //test("Pars")


        val imgBtnManageCities: ImageButton = findViewById(R.id.imgBtnManageCities)
        imgBtnManageCities.setOnClickListener{
            val intent = Intent (this, ManageCities::class.java)
            startActivity(intent)
        }

    /*
        weather= Weather.cityWeather(this,"Paris")
        if (::weather.isInitialized){
            Log.d("InitialiserM","accomplie")
        }else{
            Log.d("InitialiserM","non accomplie")
        }

     */
        //val text:TextView=findViewById(R.id.textViewCity)

    }

    private fun request(city:String){
        //lateinit var we:Weather
       val test=OpenWeatherURL
        //var city=eCity.text.toString().trim()
        val url=test.urlAPI+"?q="+city+"&appid="+test.appid
        val queue= Volley.newRequestQueue(this)
        val jsonObjectRequest= JsonObjectRequest(
            Request.Method.GET,url,null,
            Response.Listener { response ->

                setValue(response)

                Toast.makeText(this, response.toString(), Toast.LENGTH_LONG).show()
            }, Response.ErrorListener {
                Toast.makeText(this, "error", Toast.LENGTH_LONG).show()
            }

        )
        queue.add(jsonObjectRequest)
    }

    fun setValue(jsonObject: JSONObject){
        weather= Weather.jsonWeather(jsonObject)
        if(::weather.isInitialized){Log.d("Initialiser","accomplieYES")}
        else{
            Log.d("Initialiser","non accomplieYES")
        }

        textView.setText(weather.getVille())
        //emp.setText(weather.getTemp().temp.toString())
        //weather.getTemp().
        descipt.setText(weather.getWeather().description)
        speed.setText(weather.getWind().speed.toString())
    }
/*
    fun test(city: String){
        weather= OpenWeatherURL.cityWeather(this,city)
        if(::weather.isInitialized){Log.d("Initialiser","accomplieYES")}
        else{
            Log.d("Initialiser","non accomplieYES")
        }
        textView.setText(weather.getVille())
    }

 */

}