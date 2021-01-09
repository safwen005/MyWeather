package com.example.weatherapp.View.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.Utilities.toast
import com.example.weatherapp.View.activities.Home
import com.example.weatherapp.View.adapters.locations_adapter
import com.example.weatherapp.databinding.ManageLocationBinding
import com.thinkit.smartyhome.ViewModel.CommonViewModelImplementor

class Manage_Location() : Fragment() {

    lateinit var manageLocationBinding: ManageLocationBinding
    lateinit var locations_list: ArrayList<String>
    lateinit var activity: Home
    lateinit var commonViewModelImplementor: CommonViewModelImplementor


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        manageLocationBinding = ManageLocationBinding.inflate(layoutInflater)
        commonViewModelImplementor =
            ViewModelProvider(this).get(CommonViewModelImplementor::class.java)
        activity = (requireActivity() as Home)
        commonViewModelImplementor.Init(activity)
        return manageLocationBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        locations_list = ArrayList()
        repeat(5) {
            locations_list.add("Location Test")
        }
        manageLocationBinding.apply {

            LocationsRecycler.apply {
                layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, true)
                adapter = locations_adapter(locations_list)
            }
            home.setOnClickListener {
                auto.performClick()
                commonViewModelImplementor.putKey("auto_detect", auto.isChecked)
            }
          addLocation.setOnClickListener {
              activity.Navigate(search_location())
          }
        }
    }

    override fun onResume() {
        super.onResume()
        commonViewModelImplementor.getkey<Boolean>("auto_detect")?.let { auto_detect ->
            manageLocationBinding.auto.setChecked(auto_detect)
            activity.Change_Background(2)
            activity.ChangeToolbarTitle(requireActivity().getString(R.string.manage_location))
        }
    }

}