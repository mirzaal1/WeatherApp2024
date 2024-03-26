package com.ali.weatherapp.di

import com.ali.weatherapp.data.service.WeatherApi
import com.ali.weatherapp.data.remote.request.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideRepository(
        weatherApi: WeatherApi
    ): WeatherRepository {
        return WeatherRepository(
            weatherApi
        )
    }
}