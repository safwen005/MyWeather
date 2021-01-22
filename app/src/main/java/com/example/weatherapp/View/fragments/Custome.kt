package com.example.weatherapp.View.fragments

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.weatherapp.R
import com.example.weatherapp.Utilities.getDate
import com.example.weatherapp.Utilities.log
import com.example.weatherapp.Utilities.setIcon
import com.example.weatherapp.View.activities.Home
import com.example.weatherapp.data.Network.responses.Daily.Daily
import com.example.weatherapp.data.Network.responses.Data
import com.example.weatherapp.data.Network.responses.Hourly.Hourly
import com.example.weatherapp.databinding.CustomeBinding

class Custome() : Fragment() {

    lateinit var customeBinding: CustomeBinding
    lateinit var activity: Home
    var hourly: HashMap<String, String>? = null
    var daily: HashMap<String, String>? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        customeBinding = CustomeBinding.inflate(layoutInflater)
        activity = (requireActivity() as Home)
        return customeBinding.root
    }

    override fun onResume() {
        super.onResume()
        hourly?.let {
            setHourly()
            return
        }
        setDaily()
    }

    fun newHourly(hourly: HashMap<String, String>) {
        this.hourly = hourly
    }

    fun newDaily(daily: HashMap<String, String>?) {
        this.daily = daily
    }


    @SuppressLint("SetTextI18n")
    private fun setHourly() {
        customeBinding.apply {
            hourly?.apply {
                mytemp.text = get("myTemp")
                sunrisee.text = get("mySunrise")
                sunsett.text = get("mySunset")
                description.text = get("description")
                time.text = get("time")
                customeDate.text = get("description")
                Direction.text = get("direction")
                humidity.text = get("humidity")
                pressure.text = get("pressure")
                Wind.text = get("wind")
                activity.ChangeToolbarTitle(get("cityName"))
                requireActivity().setIcon(icon, get("icon").toString())
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setDaily() {
        customeBinding.apply {
            daily?.apply {
                mytemp.text = get("myTemp")
                sunrisee.text = get("mySunrise")
                sunsett.text = get("mySunset")
                description.text = get("description")
                customeDate.text = get("dateTime")
                Direction.text = get("direction")
                humidity.text = get("humidity")
                pressure.text = get("pressure")
                Wind.text = get("wind")
                activity.ChangeToolbarTitle(get("cityName"))
                requireActivity().setIcon(icon, get("icon").toString())
                time.visibility = View.GONE
            }
        }
    }
}