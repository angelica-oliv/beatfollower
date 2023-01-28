package com.angelicao.beatfollower.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val LATEST_HEART_RATE = doublePreferencesKey("latest_heart_rate")
class HealthRepositoryImpl @Inject constructor(private val dataStore: DataStore<Preferences>): HealthRepositoryApi {
    override fun getLatestHeartRate(): Flow<Double> {
        return dataStore.data.map { prefs ->
            prefs[LATEST_HEART_RATE] ?: 0.0
        }
    }

    override suspend fun storeHeartRate(heartRate: Double) {
        dataStore.edit { prefs ->
            prefs[LATEST_HEART_RATE] = heartRate
        }
    }
}