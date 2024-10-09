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
import org.mockito.Mockito.verify
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
    fun testInsertSensor() = runBlocking {
        // Arrange
        val sensor = Sensor(name = "New Sensor", url = "http://example.com")

        // Act
        repository.insertSensor(sensor)

        // Assert
        verify(sensorDao).insertSensor(sensor)
    }

    @Test
    fun testDeleteSensor() = runBlocking {
        // Arrange
        val sensor = Sensor(name = "Old Sensor", url = "http://example.com")

        // Act
        repository.deleteSensor(sensor)

        // Assert
        verify(sensorDao).deleteSensor(sensor)
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

    @Test
    fun testGetSensorDataLast24Hours() = runBlocking {
        // Arrange
        val sensor = Sensor(name = "Living Room", url = "http://example.com")
        val mockSensorDataList = listOf(
            SensorData(temperature = 22.0f, humidity = 40.0f, timestamp = "2024-10-08T10:00:00"),
            SensorData(temperature = 23.5f, humidity = 42.0f, timestamp = "2024-10-08T11:00:00"),
            SensorData(temperature = 24.0f, humidity = 43.5f, timestamp = "2024-10-08T12:00:00")
        )
        `when`(apiService.getSensorDataLast24Hours("${sensor.url}/history")).thenReturn(mockSensorDataList)

        // Act
        val sensorDataList = repository.getSensorDataLast24Hours(sensor)

        // Assert
        assertEquals(3, sensorDataList.size)
        assertEquals(22.0f, sensorDataList[0].temperature)
        assertEquals(23.5f, sensorDataList[1].temperature)
        assertEquals(24.0f, sensorDataList[2].temperature)
    }
}