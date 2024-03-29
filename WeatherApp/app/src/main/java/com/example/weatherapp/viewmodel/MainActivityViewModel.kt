package com.example.weatherapp.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.service.OpenWeatherService
import org.json.JSONObject

class MainActivityViewModel:ViewModel() {

    val weather_data=MutableLiveData<Weather>()
    val pref_cities=MutableLiveData<ArrayList<String>>()
    val threshold_json=MutableLiveData<JSONObject>()

    fun refreshWeather(context: Context,cityName: String){
        OpenWeatherService.getWeather1(context,cityName){data->
            if(data!=null){
                weather_data.value=data
            }
        }
    }

    fun refreshPrefCities(context: Context){
        pref_cities.value=DataStorageUtil.loadCitiesFromFile(context,DataStorageUtil.StorageType.EXTERNAL_DATA, "favorite_cities.txt")
        //Log.d("MainVM", "Im in getLiver")
    }

    fun refreshThreshold_json(context: Context){
        threshold_json.value=JSONObject(DataStorageUtil.loadTextFromFile(context,DataStorageUtil.StorageType.EXTERNAL_DATA, "threshold.txt"))
    }
}