package com.example.weatherapp.View.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.Utilities.setIcon
import com.example.weatherapp.data.Network.responses.Daily.Daily
import com.example.weatherapp.data.Network.responses.Data
import com.example.weatherapp.data.Network.responses.WeatherResponse
import com.example.weatherapp.databinding.DailyBinding

class daily_adapter(
    context: Context,
    val list: Array<HashMap<String, String>?>,
    val stuff: (HashMap<String, String>?) -> Unit
) :
    RecyclerView.Adapter<daily_adapter.DailyViewHolder>() {



    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): daily_adapter.DailyViewHolder {

        val DailyBinding = DailyBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return DailyViewHolder(DailyBinding)
    }

    @SuppressLint("DefaultLocale")
    override fun onBindViewHolder(holder: daily_adapter.DailyViewHolder, position: Int) {


        list[position].let { day ->
            holder.setDay(
                day?.get("time").toString(),
                day?.get("icon").toString(),
                day?.get("myTemp").toString()
            )
            holder.itemView.setOnClickListener {
                stuff(day)
            }
        }

    }

    override fun getItemCount(): Int = list.size - 1


    inner class DailyViewHolder(val View: DailyBinding) : RecyclerView.ViewHolder(View.root) {

        fun setDay(theday: String, icon: String, temp: String) {
            View.apply {
                myday.text = theday
                this.root.context.setIcon(myicon, icon)
                mytemp.text = temp

            }
        }
    }

}