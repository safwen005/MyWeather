package com.example.weatherapp.View.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.View.activities.Home
import com.example.weatherapp.View.adapters.locations_adapter
import com.example.weatherapp.databinding.ManageLocationBinding
import com.example.weatherapp.databinding.NoLocationBinding
import com.example.weatherapp.databinding.WeatherBinding

class Manage_Location() : Fragment() {

    lateinit var manageLocationBinding: ManageLocationBinding
    lateinit var locations_list:ArrayList<String>
    lateinit var activity:Home


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        manageLocationBinding = ManageLocationBinding.inflate(layoutInflater)
        activity=(requireActivity() as Home)
        return manageLocationBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        locations_list= ArrayList()
        repeat(5){
            locations_list.add("Location Test")
        }
        manageLocationBinding.LocationsRecycler.apply {
            layoutManager=LinearLayoutManager(requireContext(),RecyclerView.VERTICAL,true)
            adapter=locations_adapter(locations_list)
        }

    }

    override fun onResume() {
        super.onResume()
        activity.Change_Background(1)
    }
}