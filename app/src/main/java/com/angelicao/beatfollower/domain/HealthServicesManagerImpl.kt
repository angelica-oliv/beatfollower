package com.angelicao.beatfollower.domain

import android.util.Log
import androidx.concurrent.futures.await
import androidx.health.services.client.HealthServicesClient
import androidx.health.services.client.data.DataType
import androidx.health.services.client.data.PassiveListenerConfig
import com.angelicao.beatfollower.TAG
import javax.inject.Inject

class HealthServicesManagerImpl @Inject constructor(
    healthServicesClient: HealthServicesClient,
    private val passiveListenerConfig: PassiveListenerConfig,
    private val passiveListenerCallback: HealthListenerCallback
) : HealthServicesManagerApi {
    private val passiveMonitoringClient = healthServicesClient.passiveMonitoringClient
    override suspend fun hasHeartRateCapability(): Boolean {
        val capabilities = passiveMonitoringClient.getCapabilitiesAsync().await()
        return (DataType.HEART_RATE_BPM in capabilities.supportedDataTypesPassiveMonitoring)
    }

    override suspend fun registerForHeartRateData() {
        Log.i(TAG, "Registering listener")
        passiveMonitoringClient.setPassiveListenerCallback(
            passiveListenerConfig,
            passiveListenerCallback
        )
    }

    override suspend fun unregisterForHeartRateData() {
        Log.i(TAG, "Unregistering listeners")
        passiveMonitoringClient.clearPassiveListenerCallbackAsync().await()
    }
}