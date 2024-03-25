package com.ali.weatherapp.data.service

import com.ali.weatherapp.remote.response.WeatherEntity
import com.ali.weatherapp.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET(Constants.NetworkService.END_POINT)
    suspend fun getWeatherDataByCityName(
        @Query("q") cityName: String,
        @Query("APPID") apikey: String,
        @Query("units") units: String,
    ): Response<WeatherEntity>

    @GET(Constants.NetworkService.END_POINT)
    suspend fun getWeatherDataByLocation(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("APPID") apiKey: String,
        @Query("units") units: String,
    ): Response<WeatherEntity>
}