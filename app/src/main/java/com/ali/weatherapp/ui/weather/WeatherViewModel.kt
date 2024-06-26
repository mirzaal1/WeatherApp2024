package com.ali.weatherapp.ui.weather

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ali.weatherapp.data.model.LocationData
import com.ali.weatherapp.data.model.WeatherModel
import com.ali.weatherapp.data.remote.request.StatusCode
import com.ali.weatherapp.data.remote.request.WeatherRepository
import com.ali.weatherapp.utils.Constants
import com.ali.weatherapp.utils.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import java.net.UnknownHostException
import java.text.SimpleDateFormat
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel  @Inject constructor(
    private val weatherRepository: WeatherRepository
)  : ViewModel() {

    private val _cityName = MutableLiveData<String?>()
    val cityName: LiveData<String?> get() = _cityName

    private val _temp = MutableLiveData(0.0)
    val temp: LiveData<Double> get() = _temp

    private val _description = MutableLiveData("")
    val description: LiveData<String> get() = _description

    private val _feelsLike = MutableLiveData(0.0)
    val feelsLike: LiveData<Double> get() = _feelsLike

    private val _windSpeed = MutableLiveData(0.0)
    val windSpeed: LiveData<Double> get() = _windSpeed

    private val _humidity = MutableLiveData(0)
    val humidity: LiveData<Int> get() = _humidity

    private val _sunrise = MutableLiveData(0L)
    val sunrise: LiveData<Long> get() = _sunrise

    private val _sunset = MutableLiveData(0L)
    val sunset: LiveData<Long> get() = _sunset

    private val _pressure = MutableLiveData(0.0)
    val pressure: LiveData<Double> get() = _pressure

    private val _viewVisibility = MutableLiveData(View.GONE)
    val viewVisibility: LiveData<Int?> get() = _viewVisibility

    private val _errorMessageVisibility = MutableLiveData(View.GONE)
    val errorMessageVisibility: LiveData<Int?> get() = _errorMessageVisibility

    private val _status = MutableLiveData<Status>()
    val status: LiveData<Status> get() = _status

    private val _errorText = MutableLiveData<String>()
    val errorText: LiveData<String> get() = _errorText

    private val _data = MutableLiveData<WeatherModel?>()

    private var locationData: LocationData = LocationData(0.0, 0.0)

    private var _dataComingFromLoc: Boolean = false

    fun setCityName(cityName: String?) {
        _cityName.value = cityName
    }

    fun setLocationData(locData: LocationData) {
        locationData = locData
    }

    fun getData() {
        viewModelScope.launch {
            _status.value = Status.LOADING
            try {
                if (_cityName.value.isNullOrEmpty()) {
                    _dataComingFromLoc = true
                    _data.value = weatherRepository.getWeatherDataByLocation(locationData)
                    checkDataAvailable()
                } else {
                    _dataComingFromLoc = false
                    _data.value = weatherRepository.getWeatherDataByCityName(_cityName.value.toString())
                    checkDataAvailable()
                }
            } catch (e: UnknownHostException) {
                if (_dataComingFromLoc) {
                    _status.value = Status.DONE
                    _viewVisibility.value = View.VISIBLE
                    _errorMessageVisibility.value = View.GONE
                } else {
                    _status.value = Status.ERROR
                    _viewVisibility.value = View.GONE
                    _errorMessageVisibility.value = View.VISIBLE
                    _errorText.value = Constants.ErrorMessages.INTERNET_CONNECTION_ERROR
                }
            } catch (e: Exception) {
                _status.value = Status.ERROR
                _viewVisibility.value = View.GONE
                _errorMessageVisibility.value = View.VISIBLE
                _errorText.value = Constants.ErrorMessages.ERROR_MESSAGE
            }
        }
    }

    fun readTimestamp(timestamp: Long): String? {
        val formatter = SimpleDateFormat("hh:mm")
        val calendar: Calendar = Calendar.getInstance()
        calendar.timeInMillis = timestamp * 1000L
        return formatter.format(calendar.time)
    }

    private fun setWeatherData(weatherModel: WeatherModel) {
        _cityName.value = weatherModel.cityName
        _description.value = weatherModel.weatherStatus[0].description
        _temp.value = weatherModel.weatherData.temp.toInt().toDouble()
        _feelsLike.value = weatherModel.weatherData.feelsLike.toInt().toDouble()
        _windSpeed.value = weatherModel.windSpeed.speed
        _humidity.value = weatherModel.weatherData.humidity
        _sunrise.value = weatherModel.sunTimes.sunrise
        _sunset.value = weatherModel.sunTimes.sunset
        _pressure.value = weatherModel.weatherData.pressure
    }

    private fun checkDataAvailable() {
        when {
            StatusCode.statusCode == Constants.StatusCodes.UNAUTHORIZED_CODE -> {
                _status.value = Status.ERROR
                _viewVisibility.value = View.GONE
                _errorText.value = Constants.ErrorMessages.UNAUTHORIZED_MESSAGE
                _errorMessageVisibility.value = View.VISIBLE
            }
            StatusCode.statusCode == Constants.StatusCodes.NOT_FOUND_CODE -> {
                _status.value = Status.ERROR
                _viewVisibility.value = View.GONE
                _errorText.value = Constants.ErrorMessages.CITY_NOT_FOUND_MESSAGE
                _errorMessageVisibility.value = View.VISIBLE
            }
            StatusCode.statusCode == Constants.StatusCodes.OK_CODE || (_dataComingFromLoc) -> {
                _status.value = Status.DONE
                _viewVisibility.value = View.VISIBLE
                _errorMessageVisibility.value = View.GONE
                setWeatherData(_data.value!!)
            }
        }
    }

}

