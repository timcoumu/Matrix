package com.timcoumu.matrix.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.timcoumu.core.matrix.Matrix

class SetOfflineWorker(
    appContext: Context,
    params: WorkerParameters
) : CoroutineWorker(appContext, params) {
    override suspend fun doWork(): Result {
        return try {
            Matrix.setOffline()
            Matrix.stopObserver()
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}