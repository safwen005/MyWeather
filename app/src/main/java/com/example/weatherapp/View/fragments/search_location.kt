package com.example.weatherapp.View.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.weatherapp.R
import com.example.weatherapp.Utilities.reset
import com.example.weatherapp.View.activities.Home
import com.example.weatherapp.databinding.NoLocationBinding
import com.example.weatherapp.databinding.SearchLocationBinding
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.places.Place
import com.google.android.gms.location.places.ui.PlaceSelectionListener
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.jota.autocompletelocation.AutoCompleteLocation
import kotlinx.android.synthetic.main.search_location.*

class search_location() : Fragment() {

    lateinit var searchLocationBinding: SearchLocationBinding
    lateinit var activity: Home
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        searchLocationBinding = SearchLocationBinding.inflate(layoutInflater)
        activity = (requireActivity() as Home)
        return searchLocationBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize the AutocompleteSupportFragment.
        val autocompleteFragment =
            requireActivity().supportFragmentManager.findFragmentById(R.id.autocomplete_fragment)
                    as AutocompleteSupportFragment

        // Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(listOf(Place.Field.ID, Place.Field.NAME))

        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener,
            com.google.android.libraries.places.widget.listener.PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
            }

            override fun onPlaceSelected(p0: com.google.android.libraries.places.api.model.Place) {

            }

            override fun onError(status: Status) {
            }
        })

    }

    override fun onResume() {
        super.onResume()
        activity.ChangeToolbarTitle(requireActivity().getString(R.string.search_location))
    }

}