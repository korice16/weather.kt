package com.example.weatherapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.R
import com.example.weatherapp.service.OpenWeatherService
import com.example.weatherapp.viewmodel.DataStorageUtil
import com.example.weatherapp.viewmodel.ManageCitiesViewModel

class ManageCities : AppCompatActivity() {
    //public val urlAPI ="https://api.openweathermap.org/data/2.5/weather"
    //public val appid ="abb8e45cee0476bb9a364e0404835ad9"
    //https://api.openweathermap.org/data/2.5/weather?q=paris&lang=fr&appid=abb8e45cee0476bb9a364e0404835ad9
    private lateinit var textView:TextView
    private lateinit var btGet:Button
    private lateinit var eCity:EditText
    private lateinit var scrollViewCities:ScrollView
    private lateinit var manageCitiesViewModel: ManageCitiesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.manage_cities)
        manageCitiesViewModel=ViewModelProvider(this).get(ManageCitiesViewModel::class.java)
        manageCitiesViewModel.refreshPrefCities(this)
        scrollViewCities=findViewById<ScrollView>(R.id.ScrollerViewCities)

        textView=findViewById(R.id.textView2)
        eCity=findViewById(R.id.eCity)

        getLiveData()
        btGet=findViewById(R.id.btnGet)
        btGet.setOnClickListener {
            reqSave()
        }

        val imgBtnReturn: ImageButton = findViewById(R.id.imgBtnReturn)
        imgBtnReturn.setOnClickListener{
            finish();
        }
    }

    /**
     * Recupere les donneés du modelView
     */
    private fun getLiveData(){

        manageCitiesViewModel.pref_cities.observe(this, Observer { data ->
            val scrollView=findViewById<ScrollView>(R.id.ScrollerViewCities)
            val child = scrollView.getChildAt(0)
            scrollView.removeView(child)
            val newChild = TextView(this)
            newChild.text = txtCities(data)
            scrollView.addView(newChild)
        })
    }

    /**
     * Effectue la requete pour sauvegarder la ville dans le telephone, puis raffraichi le ViewModel apres ajout
     */
    private fun reqSave(){
        val repo= OpenWeatherService()
        var city=eCity.text.toString().trim()
        OpenWeatherService.getWeather1(this, city){data ->
            if (data!=null){
                store(data.getCity())
                manageCitiesViewModel.refreshPrefCities(this)
                getLiveData()
            }else{
                Toast.makeText(this, "Ville inexistante", Toast.LENGTH_LONG).show()
            }
        }
    }

    /**
     * Enregistre la ville dans le telephones
     */
    private fun store(city:String){
        DataStorageUtil.saveCityIntoStorage(this,
        DataStorageUtil.StorageType.EXTERNAL_DATA, "favorite_cities.txt",city)
        Toast.makeText(this, "Sucess save", Toast.LENGTH_LONG).show()
    }

    /**
     * Prends un arrayList<String> de ville et renvoie un String des villes, chacun sur une ligne
     */
    private fun txtCities(cities:ArrayList<String>):String{
        var txtCities=""
        for(citie in cities){
            txtCities+=citie+"\n"
        }
        return txtCities
    }
}