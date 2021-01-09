package com.example.weatherapp.View.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat
import com.example.weatherapp.R
import com.example.weatherapp.Utilities.*
import com.example.weatherapp.Utilities.Constants.permission_request_code
import com.example.weatherapp.View.fragments.Manage_Location
import com.example.weatherapp.View.fragments.NoLocation
import com.example.weatherapp.View.fragments.UnitSetting
import com.example.weatherapp.View.fragments.Weather
import com.example.weatherapp.databinding.HomeBinding
import com.google.android.material.card.MaterialCardView
import com.google.android.material.snackbar.Snackbar
import com.thinkit.smartyhome.ViewModel.CommonViewModelImplementor
import kotlin.properties.Delegates


class Home : AppCompatActivity(), View.OnClickListener, InternetAvailabilityListener {

    lateinit var homeBinding: HomeBinding
    lateinit var commonViewModelImplementor: CommonViewModelImplementor
    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    lateinit var toolbar: Toolbar
    lateinit var homefragment: Fragment
    lateinit var snake: Snackbar
    lateinit var appsettings: appsettings
    lateinit var InternetAvailabilityBroadcastIntent: IntentFilter
    lateinit var InternetAvailabilityBroadcast: InternetAvailabilityBroadcast
    var Current_selected_fragment = -1
    var we_already_got_the_location = false
    var last_location_hour by Delegates.notNull<Int>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        init()

        setContentView(homeBinding.root)

        commonViewModelImplementor =
            ViewModelProvider(this).get(CommonViewModelImplementor::class.java)


        commonViewModelImplementor.Init(this)

        Fullscreen()

        Prepare_nav_drawer()

        InitListeners()
        homefragment = Weather()
        Start_Navigation(0, homefragment)


//        CheckInternetAvailabilityAndLocationPermission()
//
//        DoesHaveLocationsInLocalDatabase()


    }



    private fun DoesHaveLocationsInLocalDatabase() {

        commonViewModelImplementor.GetLocationsCount().first?.let { observer ->
            observer.observe(this) { LocationsCount ->
                if (LocationsCount > 0) {
                    // get last selected location && show weather fragment
                    homefragment = Weather()
                    Start_Navigation(0, homefragment)
                    ShowLastChoosenLocation()
                    return@observe
                }
                // show No Location Fragment
                homefragment = NoLocation()
                Start_Navigation(0, homefragment)
            }
            return@let
        }
        commonViewModelImplementor.GetLocationsCount().second?.let { exception ->
            // Handle location request exception
            toast(exception.message)
        }
    }


    private fun ShowLastChoosenLocation() {

    }

    fun CheckInternetAvailabilityAndLocationPermission(ShowSnackBar: Boolean = true) {
        if (!isInternetAvailable(this)) {
            if (ShowSnackBar) {
                snake = appsettings.prepare_snake(this@Home, homeBinding.drawer, false)
                snake.show()
            }
            return
        }
        if (this.permissions(
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            )
        ) {
            GrabLocation()
            return
        }
        commonViewModelImplementor.getkey<Boolean>("location")?.let { UserForgetPermission ->
            if (UserForgetPermission) {
                if (ShowSnackBar) {
                    snake = appsettings.prepare_snake(this@Home, homeBinding.drawer)
                    snake.show()
                }
                return
            }
            request_permissions(
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            )
        }
    }

    fun ChangeToolbarTitle(text: String?) {
        homeBinding.appbar.toolbarTitle.text = text
    }

    private fun GrabLocationIntoWeatherFragment() {
        //if he accepts grab location in the weather fragment
    }


    private fun GrabLocationFromHomeActivityIntoWeatherFragment() {

    }

    private fun GrabLocation() {
        commonViewModelImplementor.GetLocation()?.observe(this) { result ->
            result?.first?.let { location ->


                Log.e("myapp", location.latitude.toString())
                we_already_got_the_location = true
                last_location_hour = getCurrentHour()
                return@observe
            }
            result.second?.let {
                // handle exception
            }
        }
    }

    fun DoesWeHaveAlreadyTheLocation(): Boolean = we_already_got_the_location

    private fun InitListeners() {

        homeBinding.navItems.apply {
            home.setOnClickListener(this@Home)
            Location.setOnClickListener(this@Home)
            autoChangeBackground.setOnClickListener(this@Home)
            statusBar.setOnClickListener(this@Home)
            radar.setOnClickListener(this@Home)
            unitSetting.setOnClickListener(this@Home)

            commonViewModelImplementor.getkey<Boolean>("auto_background")?.let { auto_background ->
                autoBackground.isChecked = auto_background
            }

            commonViewModelImplementor.getkey<Boolean>("auto_status")?.let { auto_status ->
                autoStatus.isChecked = auto_status
            }


        }


    }

    fun TimeOfNewLocation(): Boolean = (getCurrentHour() - last_location_hour >= 3)


    override fun onResume() {
        super.onResume()
        /*
        if (!::InternetAvailabilityBroadcastIntent.isInitialized) {
            InternetAvailabilityBroadcastIntent =
                IntentFilter().also { it.addAction("android.net.conn.CONNECTIVITY_CHANGE") }
            InternetAvailabilityBroadcast = InternetAvailabilityBroadcast()
        }
        registerReceiver(InternetAvailabilityBroadcast, InternetAvailabilityBroadcastIntent)
        if (!we_already_got_the_location || TimeOfNewLocation())
            CheckInternetAvailabilityAndLocationPermission(false)

         */
    }

    override fun onDestroy() {
        super.onDestroy()
        /*
        if (::InternetAvailabilityBroadcast.isInitialized) {
            unregisterReceiver(InternetAvailabilityBroadcast)
        }

         */

    }

    override fun Availability(IsInternetAvailable: Boolean) {

    }

    @SuppressLint("WrongConstant")
    fun Start_Navigation(position: Int, fragment: Fragment) {
        if (Current_selected_fragment != position)
            Navigate(fragment)
        homeBinding.drawer.closeDrawer(Gravity.START)
    }


    override fun onClick(v: View?) {
        homeBinding.navItems.apply {
            v?.let {
                when (it) {
                    home -> Start_Navigation(1, homefragment)
                    Location -> Start_Navigation(2, Manage_Location())
                    autoChangeBackground -> {
                        autoBackground.performClick()
                        commonViewModelImplementor.putKey(
                            "auto_background",
                            autoBackground.isChecked
                        )
                    }
                    statusBar -> {
                        autoStatus.performClick()
                        commonViewModelImplementor.putKey("auto_status", autoStatus.isChecked)
                    }
                    unitSetting -> Start_Navigation(4, UnitSetting())
                }
            }
        }
    }

    fun init() {
        appsettings = appsettings()
        homeBinding = HomeBinding.inflate(layoutInflater)
        toolbar = homeBinding.appbar.toolbar
    }

    fun Prepare_nav_drawer() {
        actionBarDrawerToggle = ActionBarDrawerToggle(
            this,
            homeBinding.drawer,
            toolbar,
            R.string.open,
            R.string.close
        )

        homeBinding.drawer.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        toolbar.navigationIcon =
            VectorDrawableCompat.create(resources, R.drawable.ic_navigation_icon, null)
        toolbar.inflateMenu(R.menu.toolbar)
        /*
        toolbar.setOnMenuItemClickListener {
            if (it.itemId == R.id.toolbar_location)
                WeatherTest()
            return@setOnMenuItemClickListener true
        }

         */
    }

    fun Change_Background(Fragment_Position: Int) {
        homeBinding.navItems.apply {
            Reset_Background(
                when (Current_selected_fragment) {
                    1 -> home
                    2 -> Location
                    3 -> radar
                    4 -> unitSetting
                    else -> null
                }
            )
            New_Background(
                when (Fragment_Position) {
                    1 -> home
                    2 -> Location
                    3 -> radar
                    else -> unitSetting
                }
            )

        }

        Current_selected_fragment = Fragment_Position
    }


    private fun New_Background(Item: MaterialCardView) {
        Item.strokeWidth = 2
    }


    private fun Reset_Background(Item: MaterialCardView? = null) {
        Item?.apply {
            strokeWidth = 0
        }
    }

    fun Navigate(fragment: Fragment, tag: String? = null) {
        replace_fragment(R.id.myframe, fragment, tag)
    }


    fun GoToPreviousFragment() {
        supportFragmentManager.popBackStack()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //    if (resultCode == android.app.Activity.RESULT_OK)
        //   commonViewModelImplementor.GetWeather()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        commonViewModelImplementor.apply {
            if (requestCode == permission_request_code) {
                if (ActivityCompat.checkSelfPermission(
                        this@Home,
                        permissions[0]
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    GetLocation()
                    putKey("location", false)
                    return
                }
                if (!ActivityCompat.shouldShowRequestPermissionRationale(
                        this@Home,
                        permissions[0]
                    )
                ) putKey("location", true)
            }
        }

    }

    @SuppressLint("WrongConstant")
    override fun onBackPressed() {
        if (homeBinding.drawer.isDrawerOpen(Gravity.START)) {
            homeBinding.drawer.closeDrawer(Gravity.START)
            return
        }

        if (Current_selected_fragment == 0) {
            finishAffinity()
        }
        GoToPreviousFragment()
    }


}