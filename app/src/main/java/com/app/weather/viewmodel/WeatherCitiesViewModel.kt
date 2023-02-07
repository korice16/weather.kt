package com.app.weather.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.app.weather.model.api.WeatherResponse
import com.app.weather.service.WeatherService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class WeatherCitiesViewModel @Inject constructor(
    private val apiService: WeatherService
): BaseViewModel() {
    private val onButtonClick = {
        updateWeatherCities()
    }
    private var internalState: WeatherCitiesInternalState = WeatherCitiesInternalState(
        onButtonClick = onButtonClick
    )
    val state: MutableLiveData<WeatherCitiesState> = MutableLiveData(internalState.map());

    private fun updateWeatherCities() {
        viewModelScope.launch {
            val response = apiService.getWeather("Paris", "abb8e45cee0476bb9a364e0404835ad9")

            internalState = internalState.copy(
                weatherResponse = response
            )

            state.value = internalState.map()
        }
    }
}

data class WeatherCitiesState (
    val onButtonClick: () -> Unit,
    val temperature: Double?,
    val pressure: Int?,
    val humidity: Int?
)

data class WeatherCitiesInternalState (
    val onButtonClick: () -> Unit,
    val weatherResponse: WeatherResponse? = null
) {
    fun map(): WeatherCitiesState = WeatherCitiesState(
        onButtonClick,
        weatherResponse?.basicData?.temperature,
        weatherResponse?.basicData?.pressure,
        weatherResponse?.basicData?.humidity
    )
}