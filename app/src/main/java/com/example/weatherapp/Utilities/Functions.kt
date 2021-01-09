package com.example.weatherapp.Utilities

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Paint
import android.location.Location
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import com.example.weatherapp.R
import com.example.weatherapp.Utilities.Constants.permission_request_code
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.Month
import java.time.Year
import java.util.*

fun Activity.Fullscreen() = getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)

fun delayy(time: Long, job: () -> Unit) {
    CoroutineScope(Dispatchers.Main).launch {
        delay(time)
        job()
    }
}

fun TextView.underline() {
    paintFlags = Paint.UNDERLINE_TEXT_FLAG
}

fun View.rotate_left_right(context: Context, id: Int, id2: Int) {
    startAnimation(AnimationUtils.loadAnimation(context, id).apply {
        setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {
                rotate_left_right(context, id2, id)
            }

            override fun onAnimationRepeat(animation: Animation?) {
            }

        })
    })
}

fun Context.permissions(vararg ids: String): Boolean {
    ids.forEach {
        if (ContextCompat.checkSelfPermission(
                this,
                it
            ) != PackageManager.PERMISSION_GRANTED
        ) return false
    }
    return true
}

fun AppCompatActivity.replace_fragment(id: Int, fragment: Fragment, tag: String? = null) {
    supportFragmentManager.beginTransaction().addToBackStack(null)
        .replace(R.id.myframe, fragment, tag)
        .commit()
}

fun Activity.request_permissions(vararg ids: String) {
    ActivityCompat.requestPermissions(this, ids, permission_request_code)
}

fun TextInputEditText.reset() = setText("")


fun isInternetAvailable(context: Context?): Boolean {
    var result = false
    val connectivityManager =
        context!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val networkCapabilities = connectivityManager.activeNetwork ?: return false
        val actNw = connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
        result = when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    } else {
        connectivityManager.run {
            connectivityManager.activeNetworkInfo?.run {
                result = when (type) {
                    ConnectivityManager.TYPE_WIFI -> true
                    ConnectivityManager.TYPE_MOBILE -> true
                    ConnectivityManager.TYPE_ETHERNET -> true
                    else -> false
                }

            }
        }
    }

    return result
}

fun log(text: Any?) = Log.e("myapp", text.toString())

fun Context.toast(message: Any?) =
    Toast.makeText(this, message.toString(), Toast.LENGTH_LONG).show()

fun Context.Color(id: Int) = ContextCompat.getColor(this, id)

@SuppressLint("SetTextI18n")
fun TextView.add(newtext: Any) {
    text = text.toString() + " " + newtext
}

// Get the current date !
@SuppressLint("SimpleDateFormat")
fun Context.getCurrentHour(time: Long? = null): Int {
    val date = Date(time ?: Calendar.getInstance().timeInMillis)
    date.time += 3600000
    val formatter: DateFormat = SimpleDateFormat("HH")
    formatter.setTimeZone(TimeZone.getTimeZone("UTC"))
    return formatter.format(date).toInt()
}

@SuppressLint("SimpleDateFormat")
fun Context.getFullTime(time: Long? = null): String {
    val date = Date(time ?: Calendar.getInstance().timeInMillis)
    val formatter: DateFormat = SimpleDateFormat("HH:mm")
    return formatter.format(date)
}

@SuppressLint("SimpleDateFormat")
fun Context.getDate(time: Long): String? {
    val date = Date()
    date.time = time * 1000

    val sdf = SimpleDateFormat("dd , yyyy")
    return resources.getStringArray(R.array.months)
        .get(sdf.calendar.get(Calendar.MONTH)) + " " + sdf.format(date)
}

typealias LocationResponse = Pair<Location?, Exception?>
typealias LocationCountResponse = Pair<LiveData<Int>?, Exception?>
