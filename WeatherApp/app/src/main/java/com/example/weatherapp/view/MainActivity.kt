package com.example.weatherapp.view

import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.ScrollView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.viewmodel.MainActivityViewModel
import com.example.weatherapp.viewmodel.Weather
import android.app.Notification
import android.app.NotificationChannel
import android.graphics.Color
import android.os.Build
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.work.*
import com.example.weatherapp.R
import com.example.weatherapp.viewmodel.NotificationWorkerManager
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    private lateinit var scrollerViewWeather: ScrollView
    private lateinit var spinnerCities:Spinner
    private lateinit var viewmodel: MainActivityViewModel
    private var selectedItem: Int= -1

    override fun onResume() {
        super.onResume()
        viewmodel.refreshPrefCities(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        spinnerCities=findViewById(R.id.spinnerCities)
        viewmodel=ViewModelProvider(this).get(MainActivityViewModel::class.java)
        viewmodel.refreshPrefCities(this)

        scrollerViewWeather=findViewById<ScrollView>(R.id.ScrollerViewWeather)

        spinnerSetting()
        pw1()

        val imgBtnManageCities: ImageButton = findViewById(R.id.imgBtnManageCities)
        imgBtnManageCities.setOnClickListener{
            val intent = Intent (this, ManageCities::class.java)
            startActivity(intent)
        }

    }

    /**
     * Fonction gerant les elements dans le spinner ainsi que les actions à effectuer en cas de selection
     */
    private fun spinnerSetting(){
        viewmodel.pref_cities.observe(this, Observer { data ->

            val adapter=ArrayAdapter(this, android.R.layout.simple_spinner_item, data)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerCities.adapter=adapter


            spinnerCities.onItemSelectedListener= object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {

                    if(selectedItem != pos){
                        val selectedItem = parent?.getItemAtPosition(pos).toString()
                        viewmodel.refreshWeather(applicationContext,selectedItem)
                        getLiveData()
                        this@MainActivity.selectedItem =pos
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    //code si rien n'est selectionner
                }
            }

        })

    }

    /**
     * Effectue le reglager pour faire un selecteur deroulante des villes ( liste deroulante)
     */


    private fun getLiveData(){
        Log.d("Main", "Im in getLiver")
        viewmodel.weather_data.observe(this, Observer { data ->
            if(data!=null){
                setValue(data)
            }

        })
    }

    /**
     * Ecris les donnes dans le scroller View
     */
    fun setValue(weather1: Weather){
        val child = scrollerViewWeather.getChildAt(0)
        scrollerViewWeather.removeView(child)
        val newChild = TextView(this)
        newChild.text = txtWeather(weather1)
        scrollerViewWeather.addView(newChild)
    }

    /**
     * texte contenant les données meteorologique
     */
    fun txtWeather(weather: Weather):String{
        var txt="Ville : "+weather.getCity()+"\n"+
                "Description : "+weather.getWeatherConditions().get(0).getDescription()+"\n"+
                "Temp act : "+weather.getTemp().getTemp()+" "+weather.getTemp().getTemp_unit().symbol+"\n"+
                "Temp feels_like : "+weather.getTemp().getTempFeels_like()+" "+weather.getTemp().getTemp_unit().symbol+"\n"+
                "Temp min : "+weather.getTemp().getTemp_min()+" "+weather.getTemp().getTemp_unit().symbol+"\n"+
                "Temp max : "+weather.getTemp().getTemp_max()+" "+weather.getTemp().getTemp_unit().symbol+"\n"+
                "Pressure : "+weather.getPressure().getPressure()+" "+weather.getPressure().getUnit().symbol+"\n"+
                "Humidity : "+weather.getHumidity().getHumidity()+" "+weather.getHumidity().getUnit().symbol +"\n"+
                "Wind speed : "+ weather.getWind().getSpeed()+" "+weather.getWind().getSpeedUnit().symbol+ "\n"+
                "Wind Direction : "+ weather.getWind().getDirection() + "\n"+
                "Sunrise : " + weather.getSunTime().getSunriseDate()+ "\n"+
                "Sunset : " + weather.getSunTime().getSunsetDate()

        return txt
    }
//---------------------------------------TEST NOTIFICATION---------------------------

    private fun pw1(){
        val workManager = WorkManager.getInstance(application)

        val constraints = Constraints.Builder()
            .setRequiresBatteryNotLow(true)
            .setRequiresCharging(true)
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        val request = PeriodicWorkRequestBuilder<NotificationWorkerManager>(15, TimeUnit.MINUTES)
            .setConstraints(constraints)
            //.setBackoffCriteria(BackoffPolicy.EXPONENTIAL, 30, TimeUnit.SECONDS)
            //.setInitialDelay(15, TimeUnit.MINUTES)
            .addTag("my_work")
            .build()
        workManager.enqueueUniquePeriodicWork("my_work", ExistingPeriodicWorkPolicy.KEEP, request)
    }

    private fun pw2(){
        val workManager = WorkManager.getInstance(applicationContext)

        val constraints = Constraints.Builder()
            .setRequiresBatteryNotLow(true)
            //.setRequiresCharging(true)
            //.setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        val request = PeriodicWorkRequestBuilder<NotificationWorkerManager>(15, TimeUnit.MINUTES)
            .setConstraints(constraints)
            //.setBackoffCriteria(BackoffPolicy.EXPONENTIAL, 30, TimeUnit.SECONDS)
            .addTag("my_id")
            //.setInitialDelay(30, TimeUnit.SECONDS)
            .build()
        workManager.enqueueUniquePeriodicWork("my_id",ExistingPeriodicWorkPolicy.KEEP,request)
    }

    fun activeNotif(){
        val workManager = WorkManager.getInstance(applicationContext)
        val constraints = Constraints.Builder().setRequiresBatteryNotLow(true).build()
        val periodicWork = PeriodicWorkRequestBuilder<NotificationWorkerManager>(15, TimeUnit.SECONDS)
            .setConstraints(constraints)
            .build()
        workManager.enqueue(periodicWork)
    }

    //nofication normale, 1 seule fois
    fun createNotification(context: Context, title: String, message: String) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel("channelId", "channelName", NotificationManager.IMPORTANCE_HIGH)
            notificationChannel.description = "channel description"
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            notificationManager.createNotificationChannel(notificationChannel)
        }

        val notificationBuilder = Notification.Builder(context, "channelId")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText(message)
            .setAutoCancel(true)

        notificationManager.notify(2, notificationBuilder.build())
    }

    fun supChannel(){
        val notificationManager = this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.deleteNotificationChannel("channelId")
        }
    }



}