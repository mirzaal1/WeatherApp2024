package com.ali.weatherapp.ui.weather

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.app.ActivityCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ali.weatherapp.R
import com.ali.weatherapp.data.model.LocationData
import com.ali.weatherapp.databinding.FragmentWeatherBinding
import com.ali.weatherapp.utils.Constants
import com.ali.weatherapp.utils.Status
import com.ali.weatherapp.utils.TinyDB
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WeatherFragment : Fragment() {

    private val viewModel: WeatherViewModel by viewModels()
    private lateinit var binding: FragmentWeatherBinding
    private lateinit var locationData: LocationData
    private var cityName: String? = null
    private lateinit var tinyDB :TinyDB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentWeatherBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        onBackHandler()
        tinyDB = TinyDB(requireContext())
        cityName = requireArguments().getString(Constants.Strings.CITY_NAME)

        getLocationData()

        viewModel.setCityName(cityName)
        viewModel.setLocationData(locationData)
        viewModel.getData()

        binding.viewModel = viewModel
        binding.fragmentWeather = this
        binding.lifecycleOwner = viewLifecycleOwner
        binding.searchButton.setOnClickListener {
            goToNextScreen()
        }

        viewModel.status.observe(viewLifecycleOwner) {
            setProgressBarVisibility()
        }
    }

    private fun setProgressBarVisibility() {
        if (viewModel.status.value == Status.LOADING) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun getLocationData() {
        if (requireArguments().getSerializable(Constants.Strings.LOCATION_DATA) == null) {
            locationData = LocationData(
                tinyDB.getFloat(getString(R.string.lat_key)).toDouble(),
                tinyDB.getFloat(getString(R.string.lon_key)).toDouble()
            )
        } else {
            locationData = requireArguments().getSerializable(Constants.Strings.LOCATION_DATA) as LocationData
            setLocDataToSharPref()
        }
    }

    private fun setLocDataToSharPref() {
        tinyDB.putFloat(getString(R.string.lat_key) ,locationData.latitude.toFloat() )
        tinyDB.putFloat(getString(R.string.lon_key) ,locationData.longitude.toFloat() )
    }

    private fun goToNextScreen() {
        findNavController().navigate(R.id.action_weatherFragment_to_searchCityFragment)
    }

    fun refreshLocation() {
        findNavController().navigate(
            R.id.action_weatherFragment_to_getLocationFragment,
            Bundle().apply { putBoolean(Constants.Strings.REFRESH_LOC, true) })
    }

    private fun onBackHandler() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                ActivityCompat.finishAffinity(requireActivity())
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

}