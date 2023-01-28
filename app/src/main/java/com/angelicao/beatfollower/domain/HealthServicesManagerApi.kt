package com.angelicao.beatfollower.domain

interface HealthServicesManagerApi {
    suspend fun hasHeartRateCapability(): Boolean

    suspend fun registerForHeartRateData()

    suspend fun unregisterForHeartRateData()
}