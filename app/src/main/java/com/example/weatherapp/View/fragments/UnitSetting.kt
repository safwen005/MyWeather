package com.example.weatherapp.View.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.R
import com.example.weatherapp.View.activities.Home
import com.example.weatherapp.databinding.FirstTimeSettingsBinding
import com.thinkit.smartyhome.ViewModel.CommonViewModelImplementor

class UnitSetting() : Fragment() {

    lateinit var firstTimeSettingsBinding: FirstTimeSettingsBinding
    lateinit var commonViewModelImplementor: CommonViewModelImplementor
    lateinit var activity: Home


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        firstTimeSettingsBinding = FirstTimeSettingsBinding.inflate(layoutInflater)
        commonViewModelImplementor =
            ViewModelProvider(this).get(CommonViewModelImplementor::class.java)
        activity = (requireActivity() as Home)
        commonViewModelImplementor.Init(activity)
        return firstTimeSettingsBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity.apply {
            firstTimeSettingsBinding.apply {

                InitValues()

                PerformCLicks(
                    Pair(Temperaturee, Temperature),
                    Pair(TimeFormatt, TimeFormat),
                    Pair(Precipitationn, Precipitation),
                    Pair(WindSpeedd, WindSpeed),
                    Pair(Pressuree, Pressure)
                )

                done.setOnClickListener {
                    commonViewModelImplementor.SaveSettings(
                        Pair(getString(R.string.Temperature), Temperature.selectedItemPosition),
                        Pair(getString(R.string.Time_Format), TimeFormat.selectedItemPosition),
                        Pair(getString(R.string.Precipitation), Precipitation.selectedItemPosition),
                        Pair(getString(R.string.Wind_Speed), WindSpeed.selectedItemPosition),
                        Pair(getString(R.string.Pressure), Pressure.selectedItemPosition),
                    )
                    onBackPressed()
                }


            }
        }
    }

    override fun onResume() {
        super.onResume()
        activity.Change_Background(4)
        activity.ChangeToolbarTitle(requireActivity().getString(R.string.Unit_Setting))
    }

    fun InitValues() {
        firstTimeSettingsBinding.apply {
            commonViewModelImplementor.apply {
                getkey<Int>(getString(R.string.Temperature))?.let { temp ->
                    Temperature.setSelection(temp)
                }
                getkey<Int>(getString(R.string.Time_Format))?.let { Time ->
                    TimeFormat.setSelection(Time)
                }
                getkey<Int>(getString(R.string.Precipitation))?.let { precipitation ->
                    Precipitation.setSelection(precipitation)
                }
                getkey<Int>(getString(R.string.Wind_Speed))?.let { Wind ->
                    WindSpeed.setSelection(Wind)
                }
                getkey<Int>(getString(R.string.Pressure))?.let { pressure ->
                    Pressure.setSelection(pressure)
                }
            }
        }
    }

    fun PerformCLicks(vararg Listpair: Pair<View, Spinner>) {
        Listpair.forEach { pair ->
            pair.first.setOnClickListener {
                pair.second.performClick()
            }
        }
    }
}