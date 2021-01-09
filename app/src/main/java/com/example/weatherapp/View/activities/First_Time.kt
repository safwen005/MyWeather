package com.example.weatherapp.View.activities

import android.app.Notification
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.R
import com.example.weatherapp.Utilities.Fullscreen
import com.example.weatherapp.databinding.FirstTimeSettingsBinding
import com.thinkit.smartyhome.ViewModel.CommonViewModelImplementor

class First_Time : AppCompatActivity() {

    lateinit var firstTimeSettingsBinding: FirstTimeSettingsBinding
    lateinit var commonViewModelImplementor: CommonViewModelImplementor
    lateinit var home_intent: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Init()
        setContentView(firstTimeSettingsBinding.root)
        commonViewModelImplementor =
            ViewModelProvider(this).get(CommonViewModelImplementor::class.java)

        commonViewModelImplementor.Init(this)

        Fullscreen()

    }

    private fun Init() {
        firstTimeSettingsBinding = FirstTimeSettingsBinding.inflate(layoutInflater)
        home_intent = Intent(this, Home::class.java)
        firstTimeSettingsBinding.done.setOnClickListener {
            SaveSettingsAndGoHome()
        }
    }

    private fun SaveSettingsAndGoHome() {
        SaveSettings()
        startActivity(home_intent)
    }

    private fun SaveSettings() {
        firstTimeSettingsBinding.apply {

            commonViewModelImplementor.SaveSettings(
                Pair(getString(R.string.Temperature), Temperature.selectedItemPosition),
                Pair(getString(R.string.Time_Format), TimeFormat.selectedItemPosition),
                Pair(getString(R.string.Precipitation), Precipitation.selectedItemPosition),
                Pair(getString(R.string.Wind_Speed), WindSpeed.selectedItemPosition),
                Pair(getString(R.string.Pressure), Pressure.selectedItemPosition),
            )

        }
    }
}