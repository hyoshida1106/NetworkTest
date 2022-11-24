package com.example.networktest.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.networktest.network.HeartRailsExpressApi
import com.example.networktest.network.StationInfo
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _areaList = MutableLiveData<List<String>>()
    val areaList: LiveData<List<String>> get() = _areaList

    private var _selectedArea: String? = null
    val selectedArea get() = _selectedArea

    private val _prefectureList = MutableLiveData<List<String>?>()
    val prefectureList: LiveData<List<String>?> get() = _prefectureList

    private var _selectedPrefecture: String? = null
    val selectedPrefecture get() = _selectedPrefecture

    private val _lineList = MutableLiveData<List<String>?>()
    val lineList: LiveData<List<String>?> get() = _lineList

    private var _selectedLine: String? = null
    val selectedLine get() = _selectedLine

    private val _stationInfoList = MutableLiveData<List<StationInfo>?>()
    val stationNameList: LiveData<List<StationInfo>?> get() = _stationInfoList

    private var _selectedStation = MutableLiveData<StationInfo?>()
    val selectedStation: LiveData<StationInfo?>  get() = _selectedStation

    init {
        updateAreaList()
    }

    private fun updateAreaList() {
        Log.i("MainViewModel", "updateAreaList")
        viewModelScope.launch {
            try {
                val result = HeartRailsExpressApi.retrofitService.getArea()
                _areaList.value = result.response.area
            } catch(e: java.lang.Exception) {
                _areaList.value = emptyList()
            }
        }
    }

    fun onAreaSelected(position: Int) {
        _selectedArea = _areaList.value?.get(position)
        Log.i("MainViewModel", "onAreaSelected($position -> $_selectedArea)")
        updatePrefectureList(_selectedArea)
    }

    private fun updatePrefectureList(area: String?) {
        Log.i("MainViewModel", "updatePrefectureList($area)")
        if(area != null) {
            viewModelScope.launch {
                try {
                    val result = HeartRailsExpressApi.retrofitService.getPrefectures(area)
                    _prefectureList.value = listOf("----") + result.response.prefecture
                } catch (e: java.lang.Exception) {
                    _prefectureList.value = null
                }
            }
        } else {
            _prefectureList.value = null
        }
    }

    fun onPrefectureSelected(position: Int) {
        _selectedPrefecture = if(position > 0) _prefectureList.value?.get(position) else null
        Log.i("MainViewModel", "onPrefectureSelected($position -> $_selectedPrefecture)")
        updateLineList(_selectedArea, _selectedPrefecture)
    }

    private fun updateLineList(area: String?, prefecture: String?) {
        Log.i("MainViewModel", "updateLineList($area, $prefecture)")
        if(area != null && prefecture != null) {
            viewModelScope.launch {
                try {
                    val result = HeartRailsExpressApi.retrofitService.getLines(area, prefecture)
                    _lineList.value = listOf("----") + result.response.line
                } catch (e: java.lang.Exception) {
                    _lineList.value = null
                }
            }
        } else {
            _lineList.value = null
        }
    }

    fun onLineSelected(position: Int) {
        _selectedLine = if(position > 0) _lineList.value?.get(position) else null
        Log.i("MainViewModel", "onLineSelected($position -> $_selectedLine)")
        updateStationList(_selectedLine)
    }

    private fun updateStationList(line: String?) {
        Log.i("MainViewModel", "updateStationList($line)")
        if(line != null) {
            viewModelScope.launch {
                try {
                    val result = HeartRailsExpressApi.retrofitService.getStations(line)
                    _stationInfoList.value = listOf(StationInfo("----")) + result.response.station
                } catch (e: java.lang.Exception) {
                    _stationInfoList.value = null
                }
            }
        } else {
            _stationInfoList.value = null
        }
    }

    fun onStationSelected(position: Int) {
        _selectedStation.value = if(position > 0) _stationInfoList.value?.get(position) else null
        Log.i("MainViewModel", "onStationSelected($position -> ${_selectedStation.value?.name})")
    }
}