package com.timcoumu.matrix

import android.app.Application
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.timcoumu.core.data.AppDataStore
import com.timcoumu.core.matrix.Matrix
import com.timcoumu.matrix.worker.SetOfflineWorker
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltAndroidApp
class MatrixApplication : Application() {
    @Inject
    lateinit var appDataStore: AppDataStore
    val appScope = CoroutineScope(Dispatchers.IO)
    override fun onCreate() {
        super.onCreate()
        initDataStore()
        initMatrix()
        ProcessLifecycleOwner.get().lifecycle.addObserver(
            object : DefaultLifecycleObserver {
                override fun onStart(owner: LifecycleOwner) {
//                    appScope.launch {
//                        delay(1_000)
//                        if (Matrix.isLoggedIn.value) Matrix.setOnline()
//                    }
                }
                override fun onStop(owner: LifecycleOwner) {
                    val request = OneTimeWorkRequestBuilder<SetOfflineWorker>()
                        .setConstraints(
                            Constraints.Builder()
                                .setRequiredNetworkType(NetworkType.CONNECTED)
                                .build()
                        )
                        .build()
                    WorkManager.getInstance(applicationContext).enqueue(request)
                }
            }
        )
    }


    fun initDataStore() {
        appScope.launch {
            appDataStore.preLoad()
        }
    }

    fun initMatrix() {
        val dataPath = File(this.filesDir, "matrix/data").apply { mkdirs() }.absolutePath
        val cachePath = File(this.filesDir, "matrix/cache").apply { mkdirs() }.absolutePath
        appScope.launch {
            Matrix.initClient(
                initDataPath = dataPath,
                initCachePath = cachePath,
                dataStore = appDataStore
            )
        }
    }
}