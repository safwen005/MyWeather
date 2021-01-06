package com.example.weatherapp.View.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat
import com.example.weatherapp.R
import com.example.weatherapp.Utilities.*
import com.example.weatherapp.Utilities.Constants.permission_request_code
import com.example.weatherapp.View.fragments.Manage_Location
import com.example.weatherapp.View.fragments.NoLocation
import com.example.weatherapp.View.fragments.Weather
import com.example.weatherapp.databinding.HomeBinding
import com.google.android.material.card.MaterialCardView
import com.google.android.material.snackbar.Snackbar
import com.thinkit.smartyhome.ViewModel.CommonViewModelImplementor


class Home : AppCompatActivity(), View.OnClickListener {

    lateinit var homeBinding: HomeBinding
    lateinit var commonViewModelImplementor: CommonViewModelImplementor
    lateinit var navController: NavController
    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    lateinit var toolbar: Toolbar
    lateinit var homefragment: Fragment
    lateinit var snake: Snackbar
    lateinit var appsettings: appsettings
    var Current_selected_fragment = -1
    var we_already_got_the_location = false


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

        CheckInternetAvailabilityAndLocationPermission()

        DoesHaveLocationsInLocalDatabase()


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
            // Handle room select exception
            toast(exception.message)
        }
    }


    private fun ShowLastChoosenLocation() {

    }

    private fun CheckInternetAvailabilityAndLocationPermission() {
        if (!isInternetAvailable(this)) {
            snake = appsettings.prepare_snake(this@Home, homeBinding.drawer, false)
            snake.show()
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
                snake = appsettings.prepare_snake(this@Home, homeBinding.drawer)
                snake.show()
                return
            }
            request_permissions(
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            )
        }
    }

    private fun GrabLocationIntoWeatherFragment() {
        //if he accepts grab location in the weather fragment
    }


    private fun GrabLocationFromHomeActivityIntoWeatherFragment() {

    }

    private fun GrabLocation() {
        commonViewModelImplementor.GetWeather()?.observe(this) { result ->
            result?.first?.let { location ->
                Log.e("myapp", location.latitude.toString())
                we_already_got_the_location = true
                return@observe
            }
            result.second?.let {
                // handle exception
            }
        }
    }

    private fun InitListeners() {

        homeBinding.navItems.apply {
            home.setOnClickListener(this@Home)
            Location.setOnClickListener(this@Home)
            autoChangeBackground.setOnClickListener(this@Home)
            notification.setOnClickListener(this@Home)
            statusBar.setOnClickListener(this@Home)
            radar.setOnClickListener(this@Home)
            unitSetting.setOnClickListener(this@Home)
        }


    }

    override fun onResume() {
        super.onResume()
        if (!we_already_got_the_location) {
            if (this.permissions(
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                ) && isInternetAvailable(baseContext)
            ) {
             //   GrabLocation()
            }
        }


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
                    home -> Start_Navigation(0, homefragment)
                    Location -> Start_Navigation(1, Manage_Location())
                    autoChangeBackground -> auto.performClick()
                    statusBar -> status.performClick()
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
        toolbar.setOnMenuItemClickListener {
            if (it.itemId == R.id.toolbar_location)
                CheckInternetAvailabilityAndLocationPermission()
            return@setOnMenuItemClickListener true
        }
    }

    fun Change_Background(Fragment_Position: Int) {
        homeBinding.navItems.apply {
            Reset_Background(
                when (Current_selected_fragment) {
                    0 -> home
                    1 -> Location
                    2 -> autoChangeBackground
                    3 -> notification
                    4 -> radar
                    5 -> unitSetting
                    else -> null
                }
            )
            New_Background(
                when (Fragment_Position) {
                    0 -> home
                    1 -> Location
                    3 -> notification
                    4 -> radar
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
        if (resultCode == android.app.Activity.RESULT_OK)
            commonViewModelImplementor.GetWeather()
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
                    GetWeather()
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

    override fun onBackPressed() {
        if (Current_selected_fragment == 0) {
            finishAffinity()
        }
        GoToPreviousFragment()
    }


}