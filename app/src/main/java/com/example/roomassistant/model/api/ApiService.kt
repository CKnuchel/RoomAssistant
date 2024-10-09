package com.example.roomassistant.model.api

import com.example.roomassistant.model.SensorData
import retrofit2.http.GET
import retrofit2.http.Url


interface ApiService {

    // Getting the sensor data
    @GET
    suspend fun getSensorData(@Url sensorUrl: String): SensorData
}