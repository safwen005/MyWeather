package com.example.weatherapp.Utilities

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.provider.Settings
import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import com.example.weatherapp.R
import com.google.android.material.snackbar.Snackbar


class appsettings {

    fun prepare_snake(activity: Activity, v: View, isLocation: Boolean=true): Snackbar {
        activity.apply {
            val snake = Snackbar.make(
                v,
                if (isLocation) getString(R.string.permission_required) else getString(R.string.Internet),
                Snackbar.LENGTH_LONG
            ).apply {
                view.setBackgroundColor(Color.BLACK)
                if (isLocation) {
                    setAction(getString(R.string.Accept)) {
                        startActivity(
                            Intent(
                                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                Uri.parse(getString(R.string.thepackage) + packageName)
                            ).apply { setFlags(Intent.FLAG_ACTIVITY_NEW_TASK) })
                    }

                }

                view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
                    .apply {
                        typeface = ResourcesCompat.getFont(activity, R.font.roboto)
                        gravity = Gravity.CENTER
                    }

                setActionTextColor(Color.RED)

            }
            return snake
        }
    }

}