package com.angelicao.beatfollower.domain

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager

class StartupReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action != Intent.ACTION_BOOT_COMPLETED) return

        if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.BODY_SENSORS) ==
            PackageManager.PERMISSION_GRANTED) {
            WorkManager.getInstance(context).enqueue(
                OneTimeWorkRequestBuilder<RegisterForPassiveDataWorker>().build()
            )
        }
    }
}