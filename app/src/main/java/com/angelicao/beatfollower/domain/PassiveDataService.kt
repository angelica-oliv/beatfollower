package com.angelicao.beatfollower.domain

import android.util.Log
import androidx.health.services.client.PassiveListenerService
import androidx.health.services.client.data.DataPointContainer
import androidx.health.services.client.data.DataType
import com.angelicao.beatfollower.TAG
import com.angelicao.beatfollower.data.HealthRepositoryApi
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@AndroidEntryPoint
class PassiveDataService: PassiveListenerService() {
    @Inject
    lateinit var healthRepository: HealthRepositoryApi

    override fun onNewDataPointsReceived(dataPoints: DataPointContainer) {
        runBlocking {
            dataPoints.getData(DataType.HEART_RATE_BPM).latestHeartRate()?.let {
                Log.i(TAG, "Heart Rate in service $it")
                healthRepository.storeHeartRate(it)
            }
        }
    }

}