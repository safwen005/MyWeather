package com.example.weatherapp.View.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.weatherapp.R
import com.example.weatherapp.Utilities.add
import com.example.weatherapp.Utilities.getDate
import com.example.weatherapp.Utilities.getFullTime
import com.example.weatherapp.Utilities.log
import com.example.weatherapp.View.activities.Home
import com.example.weatherapp.databinding.WeatherBinding
import kotlinx.android.synthetic.main.weather.*

class Weather() : Fragment() {

    lateinit var weatherBinding: WeatherBinding
    lateinit var activity: Home
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        weatherBinding = WeatherBinding.inflate(layoutInflater)
        activity = (requireActivity() as Home)
        return weatherBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity.commonViewModelImplementor.GetLocation()?.observe(viewLifecycleOwner) { location ->
                location?.first?.let {
                    activity.commonViewModelImplementor.GetWeather(it.latitude, it.longitude)
                        .observe(viewLifecycleOwner) {
                                MyWeather ->
                            MyWeather.data.first().apply {
                                weatherBinding.apply {
                                    temp.text=MyWeather.data.first().temp.toString()+ "°"
                                    sunrise.text =MyWeather.data.first().sunrise.toString()
                                    sunset.text = MyWeather.data.first().sunset.toString()
                                    description.text = weather.description.toString()
                                    time.text = datetime.toString()
                                    /*
                                    date.text = requireContext().getDate(dt.toLong()+timezone)
                                    min.add(main.tempMin.toString() + "°")
                                    max.add(main.tempMax.toString() + "°")
                                    pressure.add(main.pressure)
                                    humidity.add(main.humidity.toString()+" %")
                                    activity.ChangeToolbarTitle(sys.country)

                                     */
                                }
                            }
                        }
                }
            }

    }

    override fun onResume() {
        super.onResume()
        activity.Change_Background(1)
    }
}