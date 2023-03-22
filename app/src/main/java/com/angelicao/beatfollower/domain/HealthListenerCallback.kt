package com.angelicao.beatfollower.domain

import android.util.Log
import androidx.health.services.client.PassiveListenerCallback
import androidx.health.services.client.data.DataPointContainer
import androidx.health.services.client.data.DataType
import com.angelicao.beatfollower.TAG
import com.angelicao.beatfollower.data.HealthRepositoryApi
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class HealthListenerCallback @Inject constructor(private val healthRepository: HealthRepositoryApi) : PassiveListenerCallback {
    override fun onNewDataPointsReceived(dataPoints: DataPointContainer) {
        runBlocking {
            dataPoints.getData(DataType.HEART_RATE_BPM).latestHeartRate()?.let {
                Log.i(TAG, "Heart Rate in callback $it")
                healthRepository.storeHeartRate(it)
            }
        }
    }
}