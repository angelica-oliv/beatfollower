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
        unRegisterHeartRate()
    }

    fun onBodySensorPermissionGranted() {
        registerHeartRate()
    }

    fun onBodySensorPermissionRevoked() {
        unRegisterHeartRate()
    }

    private fun registerHeartRate() {
        viewModelScope.launch {
            if (healthServicesManager.hasHeartRateCapability()) {
                healthServicesManager.registerForHeartRateData()
            }
        }
    }

    private fun unRegisterHeartRate() {
        viewModelScope.launch {
            if (healthServicesManager.hasHeartRateCapability()) {
                healthServicesManager.unregisterForHeartRateData()
            }
        }
    }
}