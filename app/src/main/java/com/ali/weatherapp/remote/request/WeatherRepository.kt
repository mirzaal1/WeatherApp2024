package com.ali.weatherapp.remote.request

import com.ali.weatherapp.data.model.LocationData
import com.ali.weatherapp.data.model.WeatherModel
import com.ali.weatherapp.data.model.mapFromEntity
import com.ali.weatherapp.data.service.WeatherApi
import com.ali.weatherapp.utils.Constants
import java.net.UnknownHostException

class WeatherRepository(
    private val weatherApi: WeatherApi
) {
    suspend fun getWeatherDataByCityName(cityName: String): WeatherModel? {
        try {
            val weatherData = weatherApi.getWeatherDataByCityName(
                cityName,
                Constants.NetworkService.API_KEY,
                Constants.NetworkService.UNITS
            )
            StatusCode.statusCode = weatherData.code()
            return weatherData.body()?.mapFromEntity()
        } catch (e: UnknownHostException) {
            throw e
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun getWeatherDataByLocation(locationData: LocationData): WeatherModel? {
        try {
            val weatherData = weatherApi.getWeatherDataByLocation(
                locationData.latitude,
                locationData.longitude,
                Constants.NetworkService.API_KEY,
                Constants.NetworkService.UNITS
            )
            StatusCode.statusCode = weatherData.code()
            return weatherData.body()?.mapFromEntity()
        } catch (e: UnknownHostException) {
            throw e
        } catch (e: Exception) {
            throw e
        }
    }
}