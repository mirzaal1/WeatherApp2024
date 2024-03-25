package com.ali.weatherapp.remote.response

import com.ali.weatherapp.data.model.WeatherModel
import com.google.gson.annotations.SerializedName

data class WeatherEntity(

    @SerializedName("main")
    val weatherData: WeatherDataEntity,

    @SerializedName("wind")
    val windSpeed: WindEntity,

    @SerializedName("weather")
    val weatherStatus: List<WeatherDescEntity>,

    @SerializedName("sys")
    val sunTimes: SunEntity,

    @SerializedName("name")
    val cityName: String,
)

data class WeatherDataEntity(

    @SerializedName("temp")
    val temp: Double,

    @SerializedName("feels_like")
    val feelsLike: Double,

    @SerializedName("pressure")
    val pressure: Double,

    @SerializedName("humidity")
    val humidity: Int,
)

data class WindEntity(

    @SerializedName("speed")
    val speed: Double,
)

data class SunEntity(

    @SerializedName("sunrise")
    val sunrise: Long,

    @SerializedName("sunset")
    val sunset: Long,
)

data class WeatherDescEntity(

    @SerializedName("description")
    val description: String,
)


fun WeatherModel.mapToEntity(): WeatherEntity {
    return WeatherEntity(WeatherDataEntity(weatherData.temp, weatherData.feelsLike, weatherData.pressure, weatherData.humidity), WindEntity(windSpeed.speed), listOf(WeatherDescEntity(weatherStatus[0].description)), SunEntity(sunTimes.sunrise, sunTimes.sunset), cityName)
}