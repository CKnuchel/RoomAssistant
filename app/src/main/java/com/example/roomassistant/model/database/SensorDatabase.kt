package com.example.roomassistant.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.roomassistant.model.Sensor
import com.example.roomassistant.model.dao.SensorDao

@Database(entities = [Sensor::class], version = 1)
abstract class SensorDatabase: RoomDatabase() {
    abstract fun sensorDao(): SensorDao
}