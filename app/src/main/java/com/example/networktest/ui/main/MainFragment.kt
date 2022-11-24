package com.example.networktest.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.networktest.R
import com.example.networktest.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private lateinit var viewModel: MainViewModel

    private fun <T> getSelectedIndex(value: T?, list:List<T>): Int {
        return if(value != null) {
            val index = list.indexOf(value)
            if(index > 0) index else 0
        } else 0
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        val viewModelFactory = MainViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        val binding: FragmentMainBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        binding.lifecycleOwner = this.viewLifecycleOwner
        binding.mainViewModel = viewModel

        viewModel.areaList.observe(this.viewLifecycleOwner) { areaList ->
            val selectedArea = viewModel.selectedArea
            val index = getSelectedIndex(selectedArea, areaList)
            binding.areaSpinner.setSelection(index)
        }

        viewModel.prefectureList.observe(this.viewLifecycleOwner) { prefectureList ->
            if(prefectureList != null) {
                binding.prefectureSpinner.visibility = View.VISIBLE
                val selectedPrefecture = viewModel.selectedPrefecture
                val index = getSelectedIndex(selectedPrefecture, prefectureList)
                binding.prefectureSpinner.setSelection(index)
            } else {
                viewModel.onPrefectureSelected(0)
                binding.prefectureSpinner.visibility = View.GONE
            }
        }

        viewModel.lineList.observe(this.viewLifecycleOwner) { lineList ->
            if(lineList != null) {
                binding.lineSpinner.visibility = View.VISIBLE
                val selectedLine = viewModel.selectedLine
                val index = getSelectedIndex(selectedLine, lineList)
                binding.lineSpinner.setSelection(index)
            } else {
                viewModel.onLineSelected(0)
                binding.lineSpinner.visibility = View.GONE
            }
        }

        viewModel.stationNameList.observe(this.viewLifecycleOwner) { stationNameList ->
            if(stationNameList != null) {
                binding.stationSpinner.visibility = View.VISIBLE
                val selectedStation = viewModel.selectedStation
                val index = getSelectedIndex(selectedStation, stationNameList)
                binding.stationSpinner.setSelection(index)
            } else {
                viewModel.onStationSelected(0)
                binding.stationSpinner.visibility = View.GONE
            }
        }

        viewModel.selectedStation.observe(this.viewLifecycleOwner) { station ->
            if(station != null) {
                binding.message.text = station.text
                binding.message.visibility = View.VISIBLE
            } else {
                binding.message.visibility = View.GONE
            }
        }

        return binding.root
    }

}