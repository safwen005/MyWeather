package com.example.weatherapp.View.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.weatherapp.View.activities.Home
import com.example.weatherapp.databinding.NoLocationBinding
class NoLocation() : Fragment() {

    lateinit var noLocationBinding: NoLocationBinding
    lateinit var activity:Home

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        noLocationBinding = NoLocationBinding.inflate(layoutInflater)
        activity=(requireActivity() as Home)
        return noLocationBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    override fun onResume() {
        super.onResume()
      activity.Change_Background(0)
    }
}