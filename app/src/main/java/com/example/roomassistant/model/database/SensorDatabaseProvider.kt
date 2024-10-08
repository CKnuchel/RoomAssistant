package com.example.roomassistant.model.database

import android.content.Context
import androidx.room.Room

object SensorDatabaseProvider {

    private var instance: SensorDatabase? = null

    fun getDatabase(context: Context): SensorDatabase {
        return instance ?: synchronized(this){
            val newInstance = Room.databaseBuilder(
                context.applicationContext,
                SensorDatabase::class.java,
                "sensor_database"
            ).build()
            instance = newInstance
            newInstance
        }
    }
}