package com.example.weatherapp.View.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.R
import com.example.weatherapp.Utilities.Coroutines
import com.example.weatherapp.Utilities.Fullscreen
import com.example.weatherapp.Utilities.rotate_left_right
import com.example.weatherapp.databinding.SplashScreenBinding
import com.thinkit.smartyhome.ViewModel.CommonViewModelImplementor


class Splash_screen : AppCompatActivity() {

    lateinit var splashScreenBinding: SplashScreenBinding
    lateinit var commonViewModelImplementor: CommonViewModelImplementor
    lateinit var home_intent: Intent
    lateinit var settingse_intent: Intent


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initValues()
        setContentView(splashScreenBinding.root)
        commonViewModelImplementor =
            ViewModelProvider(this).get(CommonViewModelImplementor::class.java)
        commonViewModelImplementor.Init(this)

        GoToHome()


        Fullscreen()
/*
        Animation_left_right()

        Test()

 */
    }

    private fun initValues() {
        splashScreenBinding = SplashScreenBinding.inflate(layoutInflater)
        home_intent = Intent(this, Home::class.java)
        settingse_intent = Intent(this, First_Time::class.java)
    }

    private fun Test() {

        Coroutines.Main(3000) {
            commonViewModelImplementor.First_Time()?.let {
                GoToSettings()
                return@Main
            }
            GoToHome()
        }

    }


    private fun Animation_left_right() {
        splashScreenBinding.sun.rotate_left_right(
            baseContext,
            R.anim.rotate_sun,
            R.anim.rotate_sun2
        )
    }


    private fun GoToHome() = startActivity(home_intent)

    private fun GoToSettings() = startActivity(Intent(baseContext, First_Time::class.java))

}
