package com.angelicao.beatfollower.data

import kotlinx.coroutines.flow.Flow


interface HealthRepositoryApi {
    fun getLatestHeartRate(): Flow<Double>

    suspend fun storeHeartRate(heartRate: Double)
}