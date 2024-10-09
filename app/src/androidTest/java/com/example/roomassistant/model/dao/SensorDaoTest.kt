package com.example.roomassistant.model.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.roomassistant.model.Sensor
import com.example.roomassistant.model.database.SensorDatabase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SensorDaoTest {

    private lateinit var database: SensorDatabase
    private lateinit var sensorDao: SensorDao

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(
            context, SensorDatabase::class.java
        ).build()
        sensorDao = database.sensorDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertAndGetSingleSensor() = runBlocking {

        // Arrange
        val testSensor = Sensor(name = "TestSensor", url = "http://localhost")

        // Act
        sensorDao.insertSensor(testSensor)

        // Assert
        val sensorList = sensorDao.getAllSensors()
        Assert.assertEquals(1, sensorList.size)

        val sensor = sensorList.first()
        Assert.assertEquals(testSensor.name, sensor.name)
        Assert.assertEquals(testSensor.url, sensor.url)
    }

    @Test
    fun insertAndGetMultipleSensor() = runBlocking {

        // Arrange
        val testSensor = Sensor(name = "TestSensor", url = "http://localhost")
        val testSensor2 = Sensor(name = "TestSensor2", url = "http://localhost")
        val testSensor3 = Sensor(name = "TestSensor3", url = "http://localhost")

        // Act
        sensorDao.insertSensor(testSensor)
        sensorDao.insertSensor(testSensor2)
        sensorDao.insertSensor(testSensor3)

        // Assert
        val sensorList = sensorDao.getAllSensors()
        Assert.assertEquals(3, sensorList.size)

        var sensor = sensorList.find { s -> s.name == testSensor.name }
        Assert.assertEquals(testSensor.name, sensor?.name)
        Assert.assertEquals(testSensor.url, sensor?.url)

        sensor = sensorList.find { s -> s.name == testSensor2.name }
        Assert.assertEquals(testSensor2.name, sensor?.name)
        Assert.assertEquals(testSensor2.url, sensor?.url)

        sensor = sensorList.find { s -> s.name == testSensor3.name }
        Assert.assertEquals(testSensor3.name, sensor?.name)
        Assert.assertEquals(testSensor3.url, sensor?.url)
    }

    @Test
    fun deleteSensor() = runBlocking {

        // Arrange
        val testSensor = Sensor(id = 1, name = "TestSensor", url = "http://localhost")
        sensorDao.insertSensor(testSensor)


        // Act
        sensorDao.deleteSensor(testSensor)

        // Assert
        val sensors = sensorDao.getAllSensors()
        Assert.assertEquals(0, sensors.size)

    }
}