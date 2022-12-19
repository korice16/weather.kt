package com.example.weatherapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class ManageCities : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.manage_cities)

        val imgBtnReturn: ImageButton = findViewById(R.id.imgBtnReturn)
        imgBtnReturn.setOnClickListener{
            val intent = Intent (this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}