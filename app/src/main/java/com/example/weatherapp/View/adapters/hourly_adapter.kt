package com.example.weatherapp.View.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.Utilities.Gone
import com.example.weatherapp.Utilities.Visible
import com.example.weatherapp.Utilities.setIcon
import com.example.weatherapp.databinding.HourlyBinding

class hourly_adapter(
    val list: Array<HashMap<String, String>?>,
    val stuff: (HashMap<String, String>) -> Unit
) :
    RecyclerView.Adapter<hourly_adapter.HourlyViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): hourly_adapter.HourlyViewHolder {

        val HourBinding = HourlyBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return HourlyViewHolder(HourBinding)
    }

    override fun onBindViewHolder(holder: hourly_adapter.HourlyViewHolder, position: Int) {
        list[position]?.let { hourly ->
            holder.setHourly(hourly)
            holder.itemView.setOnClickListener {
                stuff(hourly)
            }
        }


    }

    override fun getItemCount(): Int = list.size


    inner class HourlyViewHolder(val View: HourlyBinding) : RecyclerView.ViewHolder(View.root) {

        @SuppressLint("SetTextI18n")
        fun setHourly(hourly: HashMap<String, String>) {
            hourly.apply {
                View.apply {
                    mytime.text = get("time")
                    this.root.context.setIcon(myicon, get("icon").toString(),gif)
                    mytemp.text = get("myTemp")
                }
            }
        }
    }

}