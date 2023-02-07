package com.app.weather.ui.view

import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.tooling.preview.Preview
import com.app.weather.viewmodel.WeatherCitiesState
import com.app.weather.viewmodel.WeatherCitiesViewModel

@Composable
fun WeatherCitiesScreen(viewModel: WeatherCitiesViewModel) {
    val viewModelState = viewModel.state.observeAsState()

    viewModelState.value?.let { state ->
        WeatherCitiesView(state = state)
    }
}

@Composable
fun WeatherCitiesView(state: WeatherCitiesState) {
    TextButton(onClick = state.onButtonClick) {
        Text(text = "click")
    }
    Text(text = state.temperature.toString())
}

@Preview
@Composable
fun WeatherCitiesPreview() {
    WeatherCitiesView(state = WeatherCitiesState(
        onButtonClick = {},
        temperature = 15.0,
        pressure = 20,
        humidity = 10,
    ))
}