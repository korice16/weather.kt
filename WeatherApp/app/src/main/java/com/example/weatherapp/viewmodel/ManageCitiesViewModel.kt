package com.example.weatherapp.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ManageCitiesViewModel:ViewModel() {
    val pref_cities= MutableLiveData<ArrayList<String>>()


    fun refreshPrefCities(context: Context){
        pref_cities.value=DataStorageUtil.loadCitiesFromFile(context,DataStorageUtil.StorageType.EXTERNAL_DATA, "favorite_cities.txt")
        //Log.d("MainVM", "Im in getLiver")
    }
}