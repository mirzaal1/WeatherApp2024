package com.ali.weatherapp.utils

object  Constants {
        object NetworkService {
            const val BASE_URL: String = "https://api.openweathermap.org"
            const val API_KEY: String = "7539aeb6622698c4f759e03aab09a252"
            const val UNITS: String = "metric"
            const val END_POINT = "/data/2.5/weather/"
        }

        object StatusCodes {
            const val OK_CODE = 200
            const val NOT_FOUND_CODE = 404
            const val UNAUTHORIZED_CODE = 401
        }

        object ErrorMessages {
            const val INTERNET_CONNECTION_ERROR = "No internet connection"
            const val ERROR_MESSAGE = "Something went wrong"
            const val CITY_NOT_FOUND_MESSAGE = "City not found"
            const val UNAUTHORIZED_MESSAGE = "Unauthorized Access"
        }

        object Strings {
            const val LOCATION_DATA = "locationData"
            const val CITY_NAME = "cityName"
            const val REFRESH_LOC = "refresh location"
            const val PERMISSION_ID = 101
        }


}