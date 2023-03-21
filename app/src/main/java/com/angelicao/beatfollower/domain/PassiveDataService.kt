package com.angelicao.beatfollower.domain

import android.util.Log
import androidx.health.services.client.PassiveListenerService
import androidx.health.services.client.data.DataPointContainer
import androidx.health.services.client.data.DataType
import com.angelicao.beatfollower.TAG
import com.angelicao.beatfollower.data.HealthRepositoryApi
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@AndroidEntryPoint
class PassiveDataService: PassiveListenerService() {
    @Inject
    lateinit var healthRepository: HealthRepositoryApi
    @Inject
    @Named("IODispatcher")
    lateinit var dispatcher: CoroutineDispatcher

    override fun onNewDataPointsReceived(dataPoints: DataPointContainer) {
        CoroutineScope(dispatcher).launch {
            dataPoints.getData(DataType.HEART_RATE_BPM).latestHeartRate()?.let {
                Log.i(TAG, "Heart Rate in service $it")
                healthRepository.storeHeartRate(it)
            }
        }
    }

}