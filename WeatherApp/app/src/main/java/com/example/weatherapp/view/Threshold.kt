package com.example.weatherapp.view

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.R
import com.example.weatherapp.model.datatype.json_threshold.Temperature
import com.example.weatherapp.model.datatype.json_threshold.Weather
import com.example.weatherapp.model.datatype.json_threshold.WindSpeed
import com.example.weatherapp.viewmodel.DataStorageUtil
import com.example.weatherapp.viewmodel.ThresholdViewModel
import org.json.JSONObject

class Threshold : AppCompatActivity() {
    private lateinit var btnSaveThreshold:Button
    private lateinit var thresholdViewModel:ThresholdViewModel
    private lateinit var spinnerCitiesT: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.threshold)
        thresholdViewModel=ViewModelProvider(this).get(ThresholdViewModel::class.java)
        //thresholdViewModel=ThresholdViewModel(this)
        thresholdViewModel.refreshPrefCities(this)
        thresholdViewModel.refreshThreshold_json(this)
        spinnerCitiesT=findViewById(R.id.spinnerCitiesT)

        spinnerSetting()

        btnSaveThreshold=findViewById(R.id.btnSaveThreshold)
        btnSaveThreshold.setOnClickListener {
            val temp_checkbox=findViewById<CheckBox>(R.id.checkBoxTemp)
            val temp_min=findViewById<EditText>(R.id.editTempMin)
            val temp_max=findViewById<EditText>(R.id.editTempMax)

            val windSpeed_checkbox=findViewById<CheckBox>(R.id.checkBoxWindSpeed)
            val windSpeed_min=findViewById<EditText>(R.id.editTempMin5)
            val windSpeed_max=findViewById<EditText>(R.id.editTempMax5)

            val temperature=Temperature(temp_checkbox.isChecked,temp_min.text.toString().toInt(),temp_max.text.toString().toInt())
            val windSpeed=WindSpeed(windSpeed_checkbox.isChecked,windSpeed_min.text.toString().toDouble(),windSpeed_max.text.toString().toDouble())
            val weather=Weather(spinnerCitiesT.selectedItem.toString()  ,temperature,windSpeed)

            weatherJson(weather,temperature,windSpeed)

        }
    }

    private fun weatherJson(weather: Weather, temperature: Temperature, windSpeed: WindSpeed){
        val temperatureJson = JSONObject()
        temperatureJson.put("selected", temperature.selected)
        temperatureJson.put("min", temperature.min)
        temperatureJson.put("max", temperature.max)

        val windJson = JSONObject()
        windJson.put("selected", windSpeed.selected)
        windJson.put("min", windSpeed.min)
        windJson.put("max", windSpeed.max)

        val weatherJson = JSONObject()
        weatherJson.put("city", weather.city)
        weatherJson.put("temperature", temperatureJson)
        weatherJson.put("windSpeed", windJson)

        store(weatherJson)
    }

    /**
     * Enregistre la ville dans le telephones
     */
    private fun store(json:JSONObject){
       DataStorageUtil.storeTextInfoFile(this,
            DataStorageUtil.StorageType.EXTERNAL_DATA, "threshold.txt",json.toString())


        Toast.makeText(this, "Sucess save"+json.toString(), Toast.LENGTH_LONG).show()
    }



    /**
     * Fonction gerant les elements dans le spinner ainsi que les actions à effectuer en cas de selection
     */
    private fun spinnerSetting(){
        thresholdViewModel.pref_cities.observe(this, { data ->
            val adapter=ArrayAdapter(this, android.R.layout.simple_spinner_item, data)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerCitiesT.adapter=adapter
        })
    }

}