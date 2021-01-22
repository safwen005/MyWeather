package com.example.weatherapp.Utilities

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.weatherapp.View.activities.Home


class InternetAvailabilityBroadcast : BroadcastReceiver() {


    override fun onReceive(context: Context?, intent: Intent?) {

        (context as? Home)?.apply {
            if (isInternetAvailable(this)) {
                /*
                if (TimeOfNewLocation() || !DoesWeHaveAlreadyTheLocation()) {
                    CheckInternetAvailabilityAndLocationPermission(false)
                }

                 */
            }
        }


    }


}