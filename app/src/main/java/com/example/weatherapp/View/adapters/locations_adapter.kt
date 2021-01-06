package com.example.weatherapp.View.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.databinding.LocationsBinding

class locations_adapter(val Locations: ArrayList<String>) :
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
        holder.setLocation(Locations[position])

    override fun getItemCount(): Int = Locations.size


    inner class LocationsHolder(val View: LocationsBinding) : RecyclerView.ViewHolder(View.root) {

        fun setLocation(location: String) {
            View.locationAdress.text = location
        }

    }

}