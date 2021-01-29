package com.example.weatherapp.View.fragments

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.Utilities.*
import com.example.weatherapp.View.activities.Home
import com.example.weatherapp.View.adapters.daily_adapter
import com.example.weatherapp.View.adapters.hourly_adapter
import com.example.weatherapp.data.Network.responses.Daily.Daily
import com.example.weatherapp.data.Network.responses.Data
import com.example.weatherapp.data.Network.responses.Hourly.Hourly
import com.example.weatherapp.data.Network.responses.WeatherAll
import com.example.weatherapp.data.Network.responses.myData
import com.example.weatherapp.data.db.entities.WeatherModel
import com.example.weatherapp.databinding.WeatherBinding
import dmax.dialog.SpotsDialog
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap
import kotlin.properties.Delegates

class Weather() : Fragment() {

    lateinit var weatherBinding: WeatherBinding
    lateinit var activity: Home
    var index: Int? = null

    //   lateinit var dialog: AlertDialog
    var currentt: HashMap<String, String>?=null
    var lastSelected = false
    var ok by Delegates.notNull<Boolean>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        weatherBinding = WeatherBinding.inflate(layoutInflater)
        Init()
        return weatherBinding.root
    }

    fun Init() {
        activity = requireActivity() as Home
        //   dialog = SpotsDialog.Builder().setContext(activity).build()
        activity.commonViewModelImplementor.getkey<Boolean>("first_time_location")
            ?.let { firstTime ->
                ok = firstTime
            }
    }


    override fun onResume() {
        super.onResume()
        activity.Change_Background(1)
        activity.commonViewModelImplementor.LoadLastSelectedWeather()?.let { lastSelected ->
            Log.e("myapp","last selected "+lastSelected.weatherAll.current.get("cityName"))
            DisplayWeatherALl(lastSelected.weatherAll)
            this.lastSelected = true
            index = lastSelected.id
        }
        start(ok)

    }

    fun start(ok: Boolean = false) {
        if (ok) {
            activity.CheckInternetAvailabilityAndLocationPermission {
                RequestWeather(lastSelected)
            }
        }

    }


    private fun myData.saveWeather() {
        activity.commonViewModelImplementor.saveWeather(this)?.let { exception ->
            Log.e("myapp", "insert exception : " + exception.message)
            return
        }
        activity.commonViewModelImplementor.getkey<Int>("count")?.let { count ->
            activity.commonViewModelImplementor.putKey("count", count + 1)
            activity.commonViewModelImplementor.SaveLastSelectedWeather(
                WeatherModel(
                    count + 1,
                    this
                )
            )
        }
    }

    private fun myData.updaterWeather(index: Int) {
        activity.commonViewModelImplementor.updateWeather(this, index)
        activity.commonViewModelImplementor.SaveLastSelectedWeather(
            WeatherModel(
                index,
                this
            )
        )
    }


    fun RequestWeather(update: Boolean) {
        activity.commonViewModelImplementor.apply {
            //   dialog.show()
            GetLocation()?.observe(viewLifecycleOwner) { Result ->
                Result?.first?.let { location ->
                    DisplayWeather(location, update)
                    if (!ok)
                        putKey("first_time_location", false)
                }
            }

        }
    }

    fun DisplayWeather(location: Location, update: Boolean = true) {
        activity.commonViewModelImplementor.getWeather(location.latitude, location.longitude)
            .observe(viewLifecycleOwner) { all ->
                all?.let { weatherAll ->
                    val newWeather = convertResponse(weatherAll)
                    newWeather?.let {
                        DisplayWeatherALl(newWeather)
                        if (update)
                            index?.let {
                                newWeather.updaterWeather(it)
                            }
                        else
                            newWeather.saveWeather()

                    }

                }
            }
    }

    private fun convertResponse(weatherAll: WeatherAll): myData? {

        if (weatherAll.current == null || weatherAll.hourly == null || weatherAll.daily == null) {
            return null
        }
        return myData(
            convertCurrent(weatherAll.current!!.data.first()),
            convertHourly(weatherAll.hourly!!),
            convertDaily(weatherAll.daily!!)
        )

    }


    @SuppressLint("DefaultLocale")
    private fun convertDaily(daily: List<Daily>): Array<HashMap<String, String>?> {
        val finalResult = arrayOfNulls<HashMap<String, String>>(daily.size)

        daily.forEach { day ->
            val myResult = HashMap<String, String>()

            day.apply {
                myResult.apply {
                    put(
                        "time", getDay(datetime).substring(0, 4)
                            .toUpperCase()
                    )
                    put("dateTime", requireActivity().getDate(datetime))
                    put("cityName", currentt?.get("cityName").toString())
                    put("mySunrise", currentt?.get("mySunrise").toString())
                    put("mySunset", currentt?.get("mySunset").toString())
                    put("myTemp", "$temp°")
                    put("description", weather.description)
                    put("direction", getString(R.string.direction) + " : " + windDir.toInt() + "°")
                    put("humidity", getString(R.string.humidity) + " : " + "${rh.toInt()} %")
                    put("pressure", getString(R.string.pressure) + " : " + pres.toInt() + " mb")
                    put("wind", getString(R.string.wind) + " : " + windSpd.toInt() + " m/s")
                    put("icon", weather.icon)
                }
            }
            finalResult[daily.indexOf(day)] = myResult
        }

        return finalResult
    }


    private fun convertHourly(hourly: List<Hourly>): Array<HashMap<String, String>?> {
        val finalResult = arrayOfNulls<HashMap<String, String>>(hourly.size)

        hourly.forEach { hour ->
            val myResult = HashMap<String, String>()
            hour.apply {
                myResult.apply {
                    put("time", Time.substring(11, 16))
                    put("dateTime", requireContext().getDate(datetime.substring(0, 10)))
                    put("cityName", currentt?.get("cityName").toString())
                    put("mySunrise", currentt?.get("mySunrise").toString())
                    put("mySunset", currentt?.get("mySunset").toString())
                    put("myTemp", "$temp°")
                    put("description", weather.description)
                    put("direction", getString(R.string.direction) + " : " + windDir.toInt() + "°")
                    put("humidity", getString(R.string.humidity) + " : " + "${rh.toInt()} %")
                    put("pressure", getString(R.string.pressure) + " : " + pres.toInt() + " mb")
                    put("wind", getString(R.string.wind) + " : " + windSpd.toInt() + " m/s")
                    put("icon", weather.icon)
                }
            }
            finalResult[hourly.indexOf(hour)] = myResult
        }

        return finalResult
    }


    private fun convertCurrent(data: Data): HashMap<String, String> {
        val myResult = HashMap<String, String>()
        data.apply {
            myResult.apply {
                put("myTemp", "$temp°")
                put("mySunrise", sunrise)
                put("mySunset", sunset)
                put("description", weather.description)
                put("time", getTime(obTime))
                put("date", requireActivity().getDate(obTime))
                put("direction", getString(R.string.direction) + " : " + windDir.toInt() + "°")
                put("humidity", getString(R.string.humidity) + " : " + "${rh.toInt()} %")
                put("pressure", getString(R.string.pressure) + " : " + pres.toInt() + " mb")
                put("wind", getString(R.string.wind) + " : " + windSpd.toInt() + " m/s")
                put("cityName", cityName)
                put("icon", weather.icon)
            }
        }
        currentt = myResult
        Log.e("myapp", currentt?.get("myTemp")!!)
        return currentt!!
    }


    fun DisplayWeatherALl(weatherAll: myData) {
        if (weatherBinding.noLocation.isVisible()) {
            weatherBinding.noLocation.Gone()
            weatherBinding.myweather.Visible()
        }
        //   dialog.dismiss()
        weatherAll.apply {
            setCurrent(currentt)
            setHourly(
                hourly
            )
            setDaily(
                daily
            )
        }
    }

    private fun setHourly(hourly: Array<HashMap<String, String>?>) {
        manageHourlyRecycler(hourly)
    }


    private fun manageHourlyRecycler(hourly: Array<HashMap<String, String>?>) {
        weatherBinding.HourlyRecycler.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            adapter = hourly_adapter(hourly) { data ->
                val custome = Custome()
                custome.newHourly(data)
                activity.Start_Navigation(0, custome)
            }
        }
    }

    @SuppressLint("DefaultLocale")
    private fun setDaily(daily: Array<HashMap<String, String>?>) {

        weatherBinding.DailyRecycler.apply {

            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)

            adapter = daily_adapter(requireContext(), daily) { data ->
                val custome = Custome()
                custome.newDaily(data)
                activity.Start_Navigation(0, custome)
            }
        }
    }


    @SuppressLint("SimpleDateFormat")
    private fun getDay(date: String): String {
        val simpleDateFormat = SimpleDateFormat("yyyy-M-dd")
        val d = simpleDateFormat.parse(date) as Date
        return SimpleDateFormat("EEEE", Locale.ENGLISH).format(d)
    }


    @SuppressLint("SetTextI18n")
    private fun setCurrent(data: HashMap<String, String>?) {
        data?.apply {
            weatherBinding.apply {
                mytemp.text = get("myTemp")
                sunrisee.text = get("mySunrise")
                sunsett.text = get("mySunset")
                description.text = get("description")
                time.text = get("time")
                date.text = get("date")
                Direction.text = get("direction")
                humidity.text = get("humidity")
                pressure.text = get("pressure")
                Wind.text = get("wind")
                activity.ChangeToolbarTitle(get("cityName"))
                requireActivity().setIcon(icon, get("icon").toString())
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun getTime(date: String): String {
        val s = SimpleDateFormat("yyyy-M-dd HH:mm")
        s.parse(date)
        return s.calendar.get(Calendar.HOUR_OF_DAY)
            .toString() + ":" + s.calendar.get(Calendar.MINUTE)
    }

    @SuppressLint("SimpleDateFormat")
    private fun newTime(mytime: String, add: Int): String {
        val s = SimpleDateFormat("HH:mm")
        // val d = SimpleDateFormat("yyyy-M-dd")
        //  var result: Pair<String, String>? = null
        s.parse(mytime)
        val c = s.calendar
        c.add(Calendar.HOUR_OF_DAY, add)

        val newtime = c.get(Calendar.HOUR_OF_DAY).toString() + ":" + c.get(Calendar.MINUTE)

        return newtime
    }

    @SuppressLint("SimpleDateFormat")
    private fun newDate(mydate: String): String {
        var new: Date? = null
        val c = Calendar.getInstance()
        var format = SimpleDateFormat.getInstance()
        try {
            format = SimpleDateFormat("yyyy-M-dd")
            val date = format.parse(mydate.substring(0, 11)) as Date

            c.time = date
            c.add(Calendar.DATE, 1)
        } catch (e: Exception) {
            Log.e("Exception is ", e.toString())
        }
        return format.format(c.time)
    }

}