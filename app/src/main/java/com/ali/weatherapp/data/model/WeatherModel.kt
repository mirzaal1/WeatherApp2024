package com.ali.weatherapp.data.model

import com.ali.weatherapp.remote.response.WeatherEntity

data class WeatherModel(
    val weatherData: WeatherData,
    val windSpeed: Wind,
    val weatherStatus: List<Weather>,
    val sunTimes: SunData,
    val cityName: String,
)

data class WeatherData(
    val temp: Double,
    val feelsLike: Double,
    val pressure: Double,
    val humidity: Int,
)

data class SunData(
    val sunrise: Long,
    val sunset: Long,
)

data class Weather(
    val description: String,
)

data class Wind(
    val speed: Double,
)

fun WeatherEntity.mapFromEntity(): WeatherModel {
    return WeatherModel(
        WeatherData(
            weatherData.temp,
           weatherData.feelsLike,
            weatherData.pressure,
            weatherData.humidity
        ),
        Wind(windSpeed.speed),
        listOf(Weather(weatherStatus[0].description)),
        SunData(
            sunTimes.sunrise,
            sunTimes.sunset
        ),
        cityName
    )
}
