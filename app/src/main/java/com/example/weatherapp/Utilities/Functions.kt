package com.example.weatherapp.Utilities

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.location.Location
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import com.bumptech.glide.GenericTransitionOptions
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.weatherapp.R
import com.example.weatherapp.Utilities.Constants.extension
import com.example.weatherapp.Utilities.Constants.icon_url
import com.example.weatherapp.Utilities.Constants.permission_request_code
import com.example.weatherapp.data.db.entities.WeatherModel
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import pl.droidsonroids.gif.GifDrawable
import pl.droidsonroids.gif.GifImageView
import java.text.DateFormat
import java.text.SimpleDateFormat
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

fun AppCompatActivity.replace_fragment(id: Int, fragment: Fragment) {
    supportFragmentManager.beginTransaction().addToBackStack(null)
        .replace(R.id.myframe, fragment)
        .commit()
}

fun View.isVisible(): Boolean = visibility == View.VISIBLE
fun Activity.request_permissions(vararg ids: String) {
    ActivityCompat.requestPermissions(this, ids, permission_request_code)
}

fun View.Gone() {
    visibility = View.GONE
}

fun View.Visible() {
    visibility = View.VISIBLE
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

fun Context.setIcon(inside: ImageView, icon: String, gif: GifImageView? = null) =
    Glide.with(this).load(icon_url+icon+extension).listener(object : RequestListener<Drawable> {
        override fun onLoadFailed(
            e: GlideException?,
            model: Any?,
            target: Target<Drawable>?,
            isFirstResource: Boolean
        ): Boolean {
            Log.e("myapp","onLoadFailed")
            return false
        }

        override fun onResourceReady(
            resource: Drawable?,
            model: Any?,
            target: Target<Drawable>?,
            dataSource: DataSource?,
            isFirstResource: Boolean
        ): Boolean {
            Log.e("myapp","onResourceReady")
            gif?.Gone()
            return false
        }
    }).into(inside)

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
fun Context.getDate(date: String): String {
    val s = SimpleDateFormat("yyyy-M-dd")
    s.parse(date)
    val c = s.calendar
    val months = resources.getStringArray(R.array.months)
    return months.get(c.get(Calendar.MONTH)) + " " + c.get(Calendar.DAY_OF_MONTH) + " , " + c.get(
        Calendar.YEAR
    )
}

@SuppressLint("SimpleDateFormat")
fun Context.getDate(calendar: Calendar): String {
    val s = resources.getStringArray(R.array.months)
        .get(calendar.get(Calendar.MONTH))

    return s + " " + calendar.get(Calendar.DAY_OF_MONTH) + " , " + calendar.get(Calendar.YEAR)
}


fun random(): String? {
    val generator = Random()
    val randomStringBuilder = StringBuilder()
    val randomLength = generator.nextInt(10)
    var tempChar: Char
    for (i in 0 until randomLength) {
        tempChar = (generator.nextInt(96) + 32).toChar()
        randomStringBuilder.append(tempChar)
    }
    return randomStringBuilder.toString()
}


typealias LocationResponse = Pair<Location?, Exception?>
typealias WeathersList = Pair<LiveData<List<WeatherModel>>?, Exception?>
