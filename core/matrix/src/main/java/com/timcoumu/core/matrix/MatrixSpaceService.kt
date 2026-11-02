package com.timcoumu.core.matrix

import android.util.Log
import com.timcoumu.core.matrix.Matrix.client
import com.timcoumu.core.matrix.MatrixService.syncScope
import com.timcoumu.core.matrix.model.RoomState
import com.timcoumu.core.matrix.model.SpaceState
import com.timcoumu.core.matrix.model.toRoom
import com.timcoumu.core.matrix.model.toSpaceState
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.matrix.rustcomponents.sdk.JoinRule
import org.matrix.rustcomponents.sdk.SpaceListUpdate
import org.matrix.rustcomponents.sdk.SpaceService
import org.matrix.rustcomponents.sdk.SpaceServiceJoinedSpacesListener
import org.matrix.rustcomponents.sdk.TaskHandle

object MatrixSpaceService {
    private val TAG = "SpaceService"
    val spacesFlow = MutableStateFlow<List<SpaceState>>(emptyList())
    var spaceService: SpaceService? = null
    var spaceServiceListener:TaskHandle? = null
    val roomInSelectedSpace = MutableStateFlow<List<RoomState>>(emptyList())
    suspend fun leaveSpace(spaceId: String){
        val handler = spaceService?.leaveSpace(spaceId)
        handler?.leave(listOf(spaceId))
        handler?.close()
        val currentSpaces = spaceService?.topLevelJoinedSpaces()
        spacesFlow.value = currentSpaces?.map {
            it.toSpaceState()
        }?:emptyList()
    }
    suspend fun editSpace(
        id: String,
        topic:String,
        name:String,
        avatar:String,
        joinRule: String,
    ){
        try {
            val spaceRoom = client.getRoom(id)
            spaceRoom?.setName(name)
            spaceRoom?.setTopic(topic)
            //avatar 应当是有效的 mxc:// URI。
            spaceRoom?.updateJoinRules(
                when (joinRule) {
                    "Public" -> JoinRule.Public
                    "Private" -> JoinRule.Private
                    "Invite" -> JoinRule.Invite
                    "Knock" -> JoinRule.Knock
                    else -> throw IllegalArgumentException(
                        "不支持的加入规则: $joinRule"
                    )
                }
            )
        }catch (e: CancellationException){
            throw e
        }catch (e: Exception){
            Log.d(TAG, "editSpace: 修改空间信息异常：${e}")
        }

    }
    internal fun createSpaceServices() {
        spacesFlow.update {
            emptyList()
        }
        syncScope.launch {
            try {
                spaceServiceListener?.close()
                spaceServiceListener = null
                spaceService = client.spaceService()
                // 先主动查询当前 Space 列表
                val currentSpaces = spaceService?.topLevelJoinedSpaces()
                spacesFlow.value = currentSpaces?.map {
                    it.toSpaceState()
                }?:emptyList()
                spaceServiceListener = spaceService?.subscribeToTopLevelJoinedSpaces(
                    object : SpaceServiceJoinedSpacesListener {
                        override fun onUpdate(roomUpdates: List<SpaceListUpdate>) {
                            Log.d(TAG, "onUpdate: 订阅到空间变化")
                            handleSpaceListUpdate(roomUpdates)
                        }
                    }
                )
            } catch (e: Exception) {
                Log.e(TAG, "createSpaceServices error", e)
            }
        }
    }
    private fun handleSpaceListUpdate(updates: List<SpaceListUpdate>) {
        updates.forEach { update ->
            when (update) {
                is SpaceListUpdate.Append -> {
                    Log.d(TAG, "Space Append: $update")

                    spacesFlow.update { current ->
                        current + update.values.map { it.toSpaceState() }
                    }
                }

                is SpaceListUpdate.PushBack -> {
                    Log.d(TAG, "Space PushBack: $update")

                    spacesFlow.update { current ->
                        current + update.value.toSpaceState()
                    }
                }

                is SpaceListUpdate.PushFront -> {
                    Log.d(TAG, "Space PushFront: $update")

                    spacesFlow.update { current ->
                        listOf(update.value.toSpaceState()) + current
                    }
                }

                is SpaceListUpdate.Insert -> {
                    Log.d(TAG, "Space Insert: $update")

                    spacesFlow.update { current ->
                        current.toMutableList().apply {
                            val index = update.index.toInt()

                            if (index in 0..size) {
                                add(index, update.value.toSpaceState())
                            } else {
                                Log.w(TAG, "Space Insert index out of bounds: $index")
                            }
                        }
                    }
                }

                is SpaceListUpdate.Set -> {
                    Log.d(TAG, "Space Set: $update")

                    spacesFlow.update { current ->
                        current.toMutableList().apply {
                            val index = update.index.toInt()

                            if (index in indices) {
                                this[index] = update.value.toSpaceState()
                            } else {
                                Log.w(TAG, "Space Set index out of bounds: $index")
                            }
                        }
                    }
                }

                is SpaceListUpdate.Remove -> {
                    Log.d(TAG, "Space Remove: $update")

                    spacesFlow.update { current ->
                        current.toMutableList().apply {
                            val index = update.index.toInt()

                            if (index in indices) {
                                removeAt(index)
                            } else {
                                Log.w(TAG, "Space Remove index out of bounds: $index")
                            }
                        }
                    }
                }

                is SpaceListUpdate.PopFront -> {
                    Log.d(TAG, "Space PopFront: $update")

                    spacesFlow.update { current ->
                        if (current.isNotEmpty()) current.drop(1) else current
                    }
                }

                is SpaceListUpdate.PopBack -> {
                    Log.d(TAG, "Space PopBack: $update")

                    spacesFlow.update { current ->
                        if (current.isNotEmpty()) current.dropLast(1) else current
                    }
                }

                is SpaceListUpdate.Clear -> {
                    Log.d(TAG, "Space Clear: $update")

                    spacesFlow.value = emptyList()
                }

                is SpaceListUpdate.Truncate -> {
                    Log.d(TAG, "Space Truncate: $update")

                    spacesFlow.update { current ->
                        current.take(update.length.toInt().coerceAtLeast(0))
                    }
                }

                is SpaceListUpdate.Reset -> {
                    Log.d(TAG, "Space Reset: $update")

                    spacesFlow.value = update.values.map { it.toSpaceState() }
                }
            }
        }
    }
    suspend fun getSpaceRoomList(spaceId: String){
        val spaceRoomList = spaceService?.spaceRoomList(spaceId)
        spaceRoomList?.paginate()
        Log.d(TAG, "getSpaceRoomList: 获取到房间列表,数量为${spaceRoomList?.rooms()?.size?:0}")
        roomInSelectedSpace.value = spaceRoomList?.rooms()?.map {
            it.toRoom()
        }?:emptyList()
    }
    suspend fun getSpace(id:String): SpaceState?{
        return spaceService?.getSpaceRoom(id)?.toSpaceState()
    }
}