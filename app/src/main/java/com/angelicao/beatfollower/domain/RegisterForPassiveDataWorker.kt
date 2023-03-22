package com.angelicao.beatfollower.domain

import android.content.Context
import androidx.health.services.client.HealthServices
import androidx.health.services.client.data.PassiveListenerConfig
import androidx.work.Worker
import androidx.work.WorkerParameters
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class RegisterForPassiveDataWorker @Inject constructor(
    private val appContext: Context,
    workerParams: WorkerParameters,
    private val passiveListenerConfig: PassiveListenerConfig,
    private val passiveListenerCallback: HealthListenerCallback
) : Worker(appContext, workerParams) {

    override fun doWork(): Result {
        runBlocking {
            HealthServices.getClient(appContext)
                .passiveMonitoringClient
                .setPassiveListenerCallback(
                    passiveListenerConfig,
                    passiveListenerCallback
                )
        }
        return Result.success()
    }
}