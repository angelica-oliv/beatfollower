package com.angelicao.beatfollower.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.angelicao.beatfollower.data.HealthRepositoryApi
import com.angelicao.beatfollower.domain.HealthServicesManagerApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val healthServicesManager: HealthServicesManagerApi,
    healthRepository: HealthRepositoryApi
): ViewModel() {
    val heartState = healthRepository.getLatestHeartRate()

    override fun onCleared() {
        viewModelScope.launch {
            if (healthServicesManager.hasHeartRateCapability()) {
                healthServicesManager.unregisterForHeartRateData()
            }
        }
    }

    fun onBodySensorPermissionGranted() {
        // Check that the device has the heart rate capability and progress to the next state
        // accordingly.
        viewModelScope.launch {
            if (healthServicesManager.hasHeartRateCapability()) {
                healthServicesManager.registerForHeartRateData()
            }
        }
    }

    fun onBodySensorPermissionRevoked() {
        viewModelScope.launch {
            if (healthServicesManager.hasHeartRateCapability()) {
                healthServicesManager.unregisterForHeartRateData()
            }
        }
    }
}