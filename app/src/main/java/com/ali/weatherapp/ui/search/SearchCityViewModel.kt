package com.ali.weatherapp.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SearchCityViewModel: ViewModel() {

    val currentText = MutableLiveData<String>()

}