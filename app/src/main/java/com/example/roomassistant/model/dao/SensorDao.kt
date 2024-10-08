package com.example.roomassistant.model.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.roomassistant.model.Sensor

@Dao
interface SensorDao{

    @Query("SELECT * FROM sensors")
    suspend fun getAllSensors() : List<Sensor>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSensor(sensor: Sensor)

    @Delete
    suspend fun deleteSensor(sensor: Sensor)
}