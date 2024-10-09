package com.example.roomassistant.model.dao

import com.example.roomassistant.model.Sensor
import com.example.roomassistant.model.SensorData
import com.example.roomassistant.model.api.ApiService
import com.example.roomassistant.model.repository.SensorRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class SensorRepositoryTest {


    private lateinit var repository: SensorRepository
    private val sensorDao: SensorDao = mock(SensorDao::class.java)
    private val apiService: ApiService = mock(ApiService::class.java)

    @Before
    fun setUp() {
        repository = SensorRepository(sensorDao, apiService)
    }

    @Test
    fun testGetAllSensors() = runBlocking {
        // Assert
        val mockSensors = listOf(Sensor(name = "Living Room", url = "http://example.com"))
        `when`(sensorDao.getAllSensors()).thenReturn(mockSensors)

        // Act
        val sensors = repository.getAllSensors()

        // Assert
        assertEquals(1, sensors.size)
        assertEquals("Living Room", sensors[0].name)
    }

    @Test
    fun testGetSensorData() = runBlocking {
        // Arrange
        val mockSensorData = SensorData(temperature = 22.5f, humidity = 45.3f, timestamp = "2024-10-08T12:34:56")
        `when`(apiService.getSensorData("http://example.com")).thenReturn(mockSensorData)

        // Act
        val sensorData = repository.getSensorData(Sensor(name = "Living Room", url = "http://example.com"))

        // Assert
        assertEquals(22.5f, sensorData.temperature)
        assertEquals(45.3f, sensorData.humidity)
        assertEquals("2024-10-08T12:34:56", sensorData.timestamp)
    }
}