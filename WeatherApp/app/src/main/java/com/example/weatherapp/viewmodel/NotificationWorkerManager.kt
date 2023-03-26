package com.example.weatherapp.viewmodel

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat

import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.weatherapp.R
import com.example.weatherapp.service.OpenWeatherService
import kotlinx.coroutines.*
import org.json.JSONObject
import java.util.*
import java.util.concurrent.CompletableFuture
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class NotificationWorkerManager(context: Context, workerParameters: WorkerParameters) :Worker(context, workerParameters) {
    lateinit private var threshold_json: JSONObject
    lateinit private var weather:Weather

    override fun doWork(): Result {

        try {

            refreshThreshold_json(applicationContext)
            weather = refreshWeather(applicationContext, threshold_json.getString("city")).get()

            createNotificationChannel()
            val notificationBuilder = buildNotification()
            val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())

            /*
            val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val notificationChannel = NotificationChannel("thresholdId", "threshold", NotificationManager.IMPORTANCE_HIGH)
                notificationChannel.description = "weather threshold"
                notificationChannel.enableLights(true)
                notificationChannel.lightColor = Color.RED
                notificationChannel.enableVibration(true)
                notificationManager.createNotificationChannel(notificationChannel)
            }
            Log.d("doWorkNotif",weather.getTemp().getTemp().toString()+"test ok")
            val notificationBuilder = Notification.Builder(applicationContext, "thresholdId")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Threshold")
                .setContentText(notifMessage())
                .setAutoCancel(true)

            notificationManager.notify(2, notificationBuilder.build())

             */

            return Result.success()
        }catch (e: Exception){
            Log.e("NotificationWorker", "Error in NotificationWorker", e)
            return Result.failure()
        }

    }

    //Creation du canal de notification
    private fun createNotificationChannel() {
        val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel("thresholdId", "threshold", NotificationManager.IMPORTANCE_HIGH)
            notificationChannel.description = "weather threshold"
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    //Construction de la notification
    private fun buildNotification(): Notification.Builder {
        return Notification.Builder(applicationContext, "thresholdId")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Threshold")
            .setContentText(notifMessage())
            .setAutoCancel(true)
    }

    //recuperer le reglage de seuil threshold.txt en JSON
    private fun refreshThreshold_json(context: Context){
        threshold_json= JSONObject(DataStorageUtil.loadTextFromFile(context,DataStorageUtil.StorageType.EXTERNAL_DATA, "threshold.txt"))
    }

    //Message à afficher lors de la notification
    private fun notifMessage():String{
        var txt=""
        val temperature=threshold_json.getJSONObject("temperature")
        val windSpeed=threshold_json.getJSONObject("windSpeed")

        if (temperature.getBoolean("isSelected")){
            txt+="Temp act : "+weather.getTemp().getTemp()+" "+weather.getTemp().getTemp_unit().symbol+"\n"
        }

        if(weather.getTemp().getTemp()>=8){
            txt+="Canicule :"+weather.getTemp().getTemp()+" "+weather.getTemp().getTemp_unit().symbol+"\n"
        }

        if(windSpeed.getBoolean("isSelected")){
            txt="Wind speed : "+ weather.getWind().getSpeed()+" "+weather.getWind().getSpeedUnit().symbol+ "\n"
        }

        if(weather.getWind().getSpeed()>=50){
            txt="Tornade : "+ weather.getWind().getSpeed()+" "+weather.getWind().getSpeedUnit().symbol+ "\n"
        }
        txt+="Test de fin"
        return txt
    }

    //Recuperer les informations depuis l'API en fonction de la ville selectionner dans le seuil
    private fun refreshWeather(context: Context, cityName: String): CompletableFuture<Weather> {
        val completableFuture = CompletableFuture<Weather>()//Attends un resultat pour l'envoie, pour que ce soit synchrone, getWeather1 est asynchrone
        OpenWeatherService.getWeather1(context,cityName){ data->
            if(data!=null){
                val weather = data
                Log.d("refreshWeather",weather.getTemp().getTemp().toString()+"humm")
                completableFuture.complete(weather)
            }else{
                throw IllegalArgumentException("City data could not be retrieved from the API")
            }
        }
        return completableFuture
    }
    /*
    suspend fun refreshWeather(context: Context, cityName: String): Weather {
        return suspendCoroutine { continuation ->
            OpenWeatherService.getWeather1(context, cityName) { data ->
                if (data != null) {
                    weather = data
                    Log.d("refreshWeather", weather.getTemp().getTemp().toString() + "humm")
                    continuation.resume(weather)
                } else {
                    throw IllegalArgumentException("City data could not be retrieved from the API")
                }
            }
        }
    }


     */
/*
    fun refreshWeather(context: Context, cityName: String){
        OpenWeatherService.getWeather1(context,cityName){ data->
            if(data!=null){
                weather=data
                Log.d("refreshWeather",weather.getCity()+"TESS")
            }else{
                throw IllegalArgumentException("City data could not be retrieved from the API")
            }
        }
    }

 */
    companion object {
        const val CHANNEL_ID = "thresholdId"
        const val NOTIFICATION_ID = 2
    }
}