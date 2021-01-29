package com.example.weatherapp.View.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.Utilities.WeathersList
import com.example.weatherapp.data.db.entities.WeatherModel
import com.example.weatherapp.databinding.LocationsBinding

class locations_adapter(val weathersList: List<WeatherModel>) :
    RecyclerView.Adapter<locations_adapter.LocationsHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationsHolder {

        val roomBinding = LocationsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return LocationsHolder(roomBinding)
    }

    override fun onBindViewHolder(holder: LocationsHolder, position: Int) =
        holder.setLocation(weathersList[position].weatherAll.current.get("cityName"))

    override fun getItemCount(): Int = weathersList.size


    inner class LocationsHolder(val View: LocationsBinding) : RecyclerView.ViewHolder(View.root) {

        fun setLocation(location: String?) {
            View.locationAdress.text = location
        }

    }

}