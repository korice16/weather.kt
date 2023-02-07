package com.example.weatherapp.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.json.JSONObject

class ThresholdViewModel():ViewModel() {
    //private val appContext=context2
    val pref_cities= MutableLiveData<ArrayList<String>>()
    val threshold_json=MutableLiveData<JSONObject>()

    init {
        //refreshThreshold_json()
        //refreshPrefCities(appContext)
    }

    fun refreshPrefCities(context: Context){
        pref_cities.value=DataStorageUtil.loadCitiesFromFile(context,DataStorageUtil.StorageType.EXTERNAL_DATA, "favorite_cities.txt")
    }

    fun refreshThreshold_json(context: Context){
        threshold_json.value=JSONObject(DataStorageUtil.loadTextFromFile(context,DataStorageUtil.StorageType.EXTERNAL_DATA, "threshold.txt"))
    }

}