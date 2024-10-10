package com.example.roomassistant.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomassistant.model.Sensor
import com.example.roomassistant.model.SensorData
import com.example.roomassistant.model.repository.SensorRepository
import kotlinx.coroutines.launch

class SensorListViewModel(
    private val repository: SensorRepository
) : ViewModel() {

    // Sensor list
    private val _sensorList = MutableLiveData<List<Sensor>>()
    val sensorList: LiveData<List<Sensor>> = _sensorList

    // Sensor data
    private val _sensordata = MutableLiveData<SensorData>()
    val sensorData: LiveData<SensorData> = _sensordata

    // Live data for error messages
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    // Function to load sensors from db
    fun loadSensors(){
        viewModelScope.launch {
            try {
                val sensors = repository.getAllSensors()
                _sensorList.value = sensors
            } catch(e: Exception) {
                "Failed to load sensors".also { _error.value = it }
            }
        }
    }

    // Function to add a new sensor
    fun addSensor(sensor: Sensor) {
        viewModelScope.launch {
            try {
                repository.insertSensor(sensor)
                loadSensors() // Reloading the list
            } catch (e: Exception) {
                "Failed to add the new sensor".also { _error.value = it }
            }
        }
    }

    // Function to delete a sensor
    fun deleteSensor(sensor: Sensor) {
        viewModelScope.launch {
            try {
                repository.deleteSensor(sensor)
                loadSensors() // Reloading the list
            } catch (e: Exception) {
                "Failed to delete the sensor".also { _error.value = it }
            }
        }
    }

    // Function to refresh the sensor data
    fun refreshSensorData(sensor: Sensor) {
        viewModelScope.launch {
            try {
                val updatedSensorData = repository.getSensorData(sensor)
                _sensordata.value = updatedSensorData
            } catch (e: Exception) {
                "Failed refreshing the sensor data".also { _error.value = it }
            }
        }
    }
}