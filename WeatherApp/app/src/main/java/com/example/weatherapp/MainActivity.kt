package com.example.weatherapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val imgBtnManageCities: ImageButton = findViewById(R.id.imgBtnManageCities)
        imgBtnManageCities.setOnClickListener{
            val intent = Intent (this, ManageCities::class.java)
            startActivity(intent)
        }

    }
}