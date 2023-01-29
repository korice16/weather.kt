package com.example.weatherapp.viewmodel

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat

import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.weatherapp.R

class NotificationWorkerManager(context: Context, workerParameters: WorkerParameters) :Worker(context, workerParameters) {
    override fun doWork(): Result {

        val notificationBuilder = NotificationCompat.Builder(applicationContext, "notification_channel_id")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Notification Title")
            .setContentText("This is the notification message")
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(1, notificationBuilder.build())

        return Result.success()
    }
}