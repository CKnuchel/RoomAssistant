package com.example.roomassistant.model.repository

import com.example.roomassistant.model.Sensor
import com.example.roomassistant.model.SensorData
import com.example.roomassistant.model.api.ApiService
import com.example.roomassistant.model.dao.SensorDao

class SensorRepository(
    private val sensorDao: SensorDao, // sensor db
    private val apiService: ApiService // sensor api
) {

    // Get all sensor from db
    suspend fun getAllSensors(): List<Sensor>{
        return sensorDao.getAllSensors()
    }

    // Insert a new sensor into the db
    suspend fun insertSensor(sensor: Sensor){
        sensorDao.insertSensor(sensor)
    }

    // Delete a sensor from the db
    suspend fun deleteSensor(sensor: Sensor){
        sensorDao.deleteSensor(sensor)
    }

    // Gets sensor data through the api
    suspend fun getSensorData(sensor: Sensor): SensorData{
        return apiService.getSensorData(sensor.url)
    }

    // Gets the sensor data of the last 24 hours through the api
    suspend fun getSensorDataLast24Hours(sensor: Sensor): List<SensorData> {
        return apiService.getSensorDataLast24Hours("${sensor.url}/history")
    }
}