package com.app.weather.ui.activities

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import com.app.weather.ui.view.WeatherCitiesScreen
import com.app.weather.viewmodel.WeatherCitiesViewModel


@AndroidEntryPoint
class WeatherCitiesActivity: BaseActivity() {
    private val viewModel: WeatherCitiesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            WeatherCitiesScreen(viewModel = viewModel)
        }
    }
}