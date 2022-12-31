package com.example.weatherapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.weatherapp.viewmodel.DataStorageUtil
import com.example.weatherapp.viewmodel.Weather
import org.json.JSONArray
import org.json.JSONObject
import java.io.FileOutputStream

class ManageCities : AppCompatActivity() {
    private val fileName="favorite_cities.txt"
    public val urlAPI ="https://api.openweathermap.org/data/2.5/weather"
    public val appid ="abb8e45cee0476bb9a364e0404835ad9"
    //https://api.openweathermap.org/data/2.5/weather?q=paris&appid=abb8e45cee0476bb9a364e0404835ad9
    //private val queue: RequestQueue=Volley.newRequestQueue(this)
    private lateinit var queue: RequestQueue
    private lateinit var textView:TextView
    private lateinit var btGet:Button
    private lateinit var eCity:EditText
    private lateinit var weather: Weather
    private val citylist= mutableListOf<JSONObject>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.manage_cities)
        textView=findViewById(R.id.textView2)
        eCity=findViewById(R.id.eCity)

        btGet=findViewById(R.id.btnGet)
        btGet.setOnClickListener {
            //var city=eCity.text.toString()
            request()
        }

        val imgBtnReturn: ImageButton = findViewById(R.id.imgBtnReturn)
        imgBtnReturn.setOnClickListener{

            finish();
        }
    }

    private fun request(){
        //lateinit var we:Weather
        var city=eCity.text.toString().trim()
        val url=urlAPI+"?q="+city+"&appid="+appid
        val queue= Volley.newRequestQueue(this)
        val jsonObjectRequest= JsonObjectRequest(
            Request.Method.GET,url,null,
            Response.Listener { response ->
                //Log.d("ManageCities","test")
                //Log.d("ManageCitiesAll",response.toString())
                //textView.setText("reussi")
                //weather= Weather.jsonWeather(response)
                store(response.getString("name"))
                //we= Weather.jsonWeather(response)
                //addW(response)
                //citylist.add(response)
                //Toast.makeText(this, weather.getVille(), Toast.LENGTH_LONG).show()
                setValue(response)
                Toast.makeText(this, response.toString(), Toast.LENGTH_LONG).show()
            },Response.ErrorListener {
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


    }

    private fun store(city:String){
        //var city=eCity.text.toString().trim()
        DataStorageUtil.saveCityIntoStorage(this,
        DataStorageUtil.StorageType.EXTERNAL_DATA, "favorite_cities.txt",city)
        Log.d("ManageCities","testsauvegarder")
        Toast.makeText(this, "sucess save2", Toast.LENGTH_LONG).show()
    }
}