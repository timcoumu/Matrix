package com.timcoumu.core.matrix

import android.util.Log
import com.google.gson.Gson
import com.timcoumu.core.matrix.Matrix.client
import com.timcoumu.core.matrix.model.MyRoomPreset
import com.timcoumu.core.matrix.model.RoomType
import com.timcoumu.core.matrix.model.toRoomPreset
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.matrix.rustcomponents.sdk.CreateRoomParameters
import org.matrix.rustcomponents.sdk.RoomVisibility
import org.matrix.rustcomponents.sdk.SyncService
import org.matrix.rustcomponents.sdk.SyncServiceState
import org.matrix.rustcomponents.sdk.SyncServiceStateObserver

object MatrixService {
    private val TAG = "MatrixService"
    val syncScope = CoroutineScope(Dispatchers.IO)
    lateinit var syncService: SyncService
    //    private var roomListService: RoomListService? = null
//    private var entriesResult: RoomListEntriesWithDynamicAdaptersResult? = null
    fun initService() {
        syncScope.launch {
            createBaseServices()
            MatrixRoomService.createRoomService()
        }
        MatrixSpaceService.createSpaceServices()
    }
    private suspend fun createBaseServices() {
            try {
                Log.d(TAG, "1. createRoomServices started")
                syncService = client.syncService().finish()
                Log.d(TAG, "2. SyncService created")
                syncService.state(
                    listener = object : SyncServiceStateObserver {
                        override fun onUpdate(state: SyncServiceState) {
                            Log.d(TAG, "SyncService state = $state")
                        }
                    }
                )
                syncService.start()
                Log.d(TAG, "3. SyncService started")
            } catch (e: Exception) {
                Log.e(TAG, "createRoomServices error", e)
            }
        }


}
