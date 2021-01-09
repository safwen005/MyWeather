package com.example.weatherapp.View.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.weatherapp.R
import com.example.weatherapp.Utilities.reset
import com.example.weatherapp.View.activities.Home
import com.example.weatherapp.databinding.NoLocationBinding
import com.example.weatherapp.databinding.SearchLocationBinding

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

        searchLocationBinding.reset.setOnClickListener {
            searchLocationBinding.search.reset()
        }
    }

    override fun onResume() {
        super.onResume()
        activity.ChangeToolbarTitle(requireActivity().getString(R.string.search_location))
    }

}