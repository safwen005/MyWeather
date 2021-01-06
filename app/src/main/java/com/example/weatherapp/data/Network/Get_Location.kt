package com.example.weatherapp.data.Network

import android.app.Activity
import android.location.Geocoder
import android.location.Location
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weatherapp.Utilities.*
import com.example.weatherapp.Utilities.Constants.settings_request_code
import com.example.weatherapp.data.Repository.WeatherRepository
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

class Get_Location(val activity: Activity) {

    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var locationRequest: LocationRequest
    lateinit var locationCallback: LocationCallback
    lateinit var gcd: Geocoder

    lateinit var locationSettingsRequest: LocationSettingsRequest
    lateinit var settingsClient: SettingsClient
    lateinit var locationsettingsresulttask: Task<LocationSettingsResponse>

    lateinit var appsettings: appsettings
    lateinit var resultformat: LocationResponse

    val result = MutableLiveData<LocationResponse>()


    fun prepare_location() {
        if (!::fusedLocationProviderClient.isInitialized) {
            appsettings = appsettings()
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity)
            locationRequest = LocationRequest.create()
            locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            locationRequest.interval = 5000
            locationCallback = object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult?) {
                    locationResult?.lastLocation?.let { location ->
                        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
                        try {
                            resultformat = LocationResponse(location, null)
                            result.value = resultformat

                        } catch (exception: Exception) {
                            resultformat = LocationResponse(null, exception)
                            result.value = resultformat
                        } finally {
                            result.value = resultformat
                        }

                    }
                }
            }
            gcd = Geocoder(activity, Locale.getDefault())
        }
    }

    fun start() {
        prepare_location()
        manage_location()
    }

    fun manage_location() {
        if (!::locationSettingsRequest.isInitialized) {
            locationSettingsRequest =
                LocationSettingsRequest.Builder()
                    .addLocationRequest(locationRequest).build()
            settingsClient = LocationServices.getSettingsClient(activity)

            locationsettingsresulttask =
                settingsClient.checkLocationSettings(locationSettingsRequest)
        }

        start_requesting()
    }

    fun start_requesting() {
        locationsettingsresulttask.addOnSuccessListener {

            fusedLocationProviderClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                activity.mainLooper
            )
            fusedLocationProviderClient.lastLocation

        }.addOnFailureListener {
            // dismiss dialog
            if (it is ResolvableApiException) {
                try {
                    it.startResolutionForResult(activity, settings_request_code)
                } catch (exception: Exception) {
                    resultformat = LocationResponse(null, exception)
                    result.value = resultformat
                }
            }
        }
    }
}


