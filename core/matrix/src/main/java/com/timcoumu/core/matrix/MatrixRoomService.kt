package com.timcoumu.core.matrix

import android.util.Log
import com.google.gson.Gson
import com.timcoumu.core.matrix.Matrix.client

import com.timcoumu.core.matrix.MatrixService.syncScope
import com.timcoumu.core.matrix.MatrixService.syncService
import com.timcoumu.core.matrix.model.MessageState
import com.timcoumu.core.matrix.model.MyRoomMemberState
import com.timcoumu.core.matrix.model.MyRoomPreset
import com.timcoumu.core.matrix.model.RoomState
import com.timcoumu.core.matrix.model.RoomType
import com.timcoumu.core.matrix.model.RoomtoRoomState
import com.timcoumu.core.matrix.model.toMyRoomMemberState
import com.timcoumu.core.matrix.model.toRoomPreset
import com.timcoumu.core.matrix.model.toRoomState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.matrix.rustcomponents.sdk.CreateRoomParameters
import org.matrix.rustcomponents.sdk.MessageType
import org.matrix.rustcomponents.sdk.MsgLikeKind
import org.matrix.rustcomponents.sdk.Room
import org.matrix.rustcomponents.sdk.RoomListEntriesDynamicFilterKind
import org.matrix.rustcomponents.sdk.RoomListEntriesListener
import org.matrix.rustcomponents.sdk.RoomListEntriesUpdate
import org.matrix.rustcomponents.sdk.RoomListEntriesWithDynamicAdaptersResult
import org.matrix.rustcomponents.sdk.RoomMember
import org.matrix.rustcomponents.sdk.RoomMembersIterator
import org.matrix.rustcomponents.sdk.RoomVisibility
import org.matrix.rustcomponents.sdk.TaskHandle
import org.matrix.rustcomponents.sdk.TimelineDiff
import org.matrix.rustcomponents.sdk.TimelineItem
import org.matrix.rustcomponents.sdk.TimelineItemContent
import org.matrix.rustcomponents.sdk.TimelineListener

object MatrixRoomService {
    private val TAG = "MatrixRoomService"
    val roomsFlow = MutableStateFlow<List<RoomState>>(emptyList())
    val roomMembersFlow = MutableStateFlow<List<MyRoomMemberState>>(emptyList())
    var timelineListener: TaskHandle? = null
    var currentRoom: Room? = null
    var roomListAdapter: RoomListEntriesWithDynamicAdaptersResult? = null
    val messageFlowTemp = MutableStateFlow<List<MessageState>>(emptyList())
    internal suspend fun createRoomService() {
        val roomListService = syncService.roomListService()
        val allRooms = roomListService?.allRooms()
        roomListAdapter = allRooms?.entriesWithDynamicAdapters(
            pageSize = 200u,
            listener = object : RoomListEntriesListener {
                override fun onUpdate(
                    roomEntriesUpdate: List<RoomListEntriesUpdate>
                ) {
                    Log.d(
                        TAG,
                        "6. onUpdate called, size=${roomEntriesUpdate.size}"
                    )

                    syncScope.launch {
                        handleRoomListUpdates(roomEntriesUpdate)
                    }
                }
            }
        )
        val controller = roomListAdapter?.controller()
        // 在这里设置适合当前 SDK 版本的 filter。
        controller?.setFilter(
            RoomListEntriesDynamicFilterKind.All(
                listOf(
                    RoomListEntriesDynamicFilterKind.Joined,
                    RoomListEntriesDynamicFilterKind.NonSpace
                )
            )
        )
        controller?.addOnePage()

    }

    private fun handleRoomListUpdates(updates: List<RoomListEntriesUpdate>) {
        updates.forEach { update ->
            when (update) {
                is RoomListEntriesUpdate.Append -> {
                    Log.d(TAG, "Append: $update")
                    roomsFlow.update { current ->
                        current + update.values.map { it.toRoomState() }
                    }
                }

                is RoomListEntriesUpdate.PushBack -> {
                    Log.d(TAG, "PushBack: $update")

                    roomsFlow.update { current ->
                        current + RoomtoRoomState(update.value)
                    }
                }

                is RoomListEntriesUpdate.PushFront -> {
                    Log.d(TAG, "PushFront: $update")

                    roomsFlow.update { current ->
                        listOf(RoomtoRoomState(update.value)) + current
                    }
                }

                is RoomListEntriesUpdate.Insert -> {
                    Log.d(TAG, "Insert: $update")

                    roomsFlow.update { current ->
                        current.toMutableList().apply {
                            val index = update.index.toInt()
                            if (index in 0..size) {
                                add(index, RoomtoRoomState(update.value))
                            } else {
                                Log.w(TAG, "Insert index out of bounds: $index")
                            }
                        }
                    }
                }

                is RoomListEntriesUpdate.Set -> {
                    Log.d(TAG, "Set: $update")

                    roomsFlow.update { current ->
                        current.toMutableList().apply {
                            val index = update.index.toInt()
                            if (index in indices) {
                                this[index] = RoomtoRoomState(update.value)
                            } else {
                                Log.w(TAG, "Set index out of bounds: $index")
                            }
                        }
                    }
                }

                is RoomListEntriesUpdate.Remove -> {
                    Log.d(TAG, "Remove: $update")

                    roomsFlow.update { current ->
                        current.toMutableList().apply {
                            val index = update.index.toInt()
                            if (index in indices) {
                                removeAt(index)
                            } else {
                                Log.w(TAG, "Remove index out of bounds: $index")
                            }
                        }
                    }
                }

                is RoomListEntriesUpdate.PopFront -> {
                    Log.d(TAG, "PopFront: $update")

                    roomsFlow.update { current ->
                        if (current.isNotEmpty()) current.drop(1) else current
                    }
                }

                is RoomListEntriesUpdate.PopBack -> {
                    Log.d(TAG, "PopBack: $update")

                    roomsFlow.update { current ->
                        if (current.isNotEmpty()) current.dropLast(1) else current
                    }
                }

                is RoomListEntriesUpdate.Clear -> {
                    Log.d(TAG, "Clear: $update")

                    roomsFlow.value = emptyList()
                }

                is RoomListEntriesUpdate.Truncate -> {
                    Log.d(TAG, "Truncate: $update")

                    roomsFlow.update { current ->
                        current.take(update.length.toInt().coerceAtLeast(0))
                    }
                }

                is RoomListEntriesUpdate.Reset -> {
                    Log.d(TAG, "Reset: $update")

                    roomsFlow.value = update.values.map { it.toRoomState() }
                }
            }
        }
    }

    suspend fun registerTimelineForOneRoom(roomId: String) {
        val room = client.getRoom(roomId)
        if (currentRoom != room) timelineListener = null
        currentRoom = room
        val timeLine = room?.timeline()
        timelineListener ?: let {
            timelineListener = timeLine?.addListener(
                object : TimelineListener {
                    override fun onUpdate(diff: List<TimelineDiff>) {
                        Log.d(TAG, "onUpdate: 订阅到房间消息发生变化${diff}")
                        for (change in diff) {
                            when (change) {
                                is TimelineDiff.Append -> {
                                    val messages = change.values.mapNotNull {
                                        parseTimelineItem(it)
                                    }
                                    messageFlowTemp.update { it + messages }
                                }

                                is TimelineDiff.Clear -> {
                                    messageFlowTemp.value = emptyList()
                                }

                                is TimelineDiff.Insert -> {
                                    val msg = parseTimelineItem(change.value)
                                        ?: continue

                                    messageFlowTemp.update { old ->
                                        old.toMutableList().apply {
                                            val index = change.index.toInt()
                                                .coerceIn(0, size)
                                            add(index, msg)
                                        }
                                    }
                                }

                                is TimelineDiff.PopBack -> {
                                    messageFlowTemp.update { old ->
                                        if (old.isEmpty()) old else old.dropLast(1)
                                    }
                                }

                                is TimelineDiff.PopFront -> {
                                    messageFlowTemp.update { old ->
                                        if (old.isEmpty()) old else old.drop(1)
                                    }
                                }

                                is TimelineDiff.PushBack -> {
                                    val msg = parseTimelineItem(change.value)
                                        ?: continue
                                    messageFlowTemp.update { it + msg }
                                }

                                is TimelineDiff.PushFront -> {
                                    val msg = parseTimelineItem(change.value)
                                        ?: continue
                                    messageFlowTemp.update { listOf(msg) + it }
                                }

                                is TimelineDiff.Remove -> {
                                    messageFlowTemp.update { old ->
                                        val index = change.index.toInt()
                                        if (index !in old.indices) {
                                            old
                                        } else {
                                            old.toMutableList().apply {
                                                removeAt(index)
                                            }
                                        }
                                    }
                                }

                                is TimelineDiff.Reset -> {
                                    val messages = change.values.mapNotNull {
                                        parseTimelineItem(it)
                                    }
                                    messageFlowTemp.value = emptyList()
                                    messageFlowTemp.value = messages
                                }

                                is TimelineDiff.Set -> {
                                    val msg = parseTimelineItem(change.value)
                                        ?: continue

                                    messageFlowTemp.update { old ->
                                        val index = change.index.toInt()
                                        if (index !in old.indices) {
                                            old
                                        } else {
                                            old.toMutableList().apply {
                                                set(index, msg)
                                            }
                                        }
                                    }
                                }

                                is TimelineDiff.Truncate -> {
                                    messageFlowTemp.update { old ->
                                        old.take(
                                            change.length.toInt().coerceAtMost(old.size)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            )
        }

    }

    private fun parseTimelineItem(item: TimelineItem): MessageState? {
        val event = item.asEvent() ?: return null
        val timelineContent = event.content

        if (timelineContent !is TimelineItemContent.MsgLike) {
            return null
        }

        return when (val kind = timelineContent.content.kind) {
            is MsgLikeKind.Message -> {
                val message = kind.content
                MessageState(
                    id = event.eventOrTransactionId.toString(),
                    sender = event.sender,
                    body = message.body,
                    msgType = when (message.msgType) {
                        is MessageType.Text -> "Text"
                        is MessageType.Emote -> "Emote"
                        is MessageType.Notice -> "Notice"
                        is MessageType.Image -> "Image"
                        is MessageType.Video -> "Video"
                        is MessageType.Audio -> "Audio"
                        is MessageType.File -> "File"
                        is MessageType.Gallery -> "Gallery"
                        is MessageType.Location -> "Location"
                        is MessageType.Other -> "Other"
                    },
                    isEdited = message.isEdited,
                    isAvailable = true
                )
            }

            is MsgLikeKind.Sticker -> {
                MessageState(
                    id = event.eventOrTransactionId.toString(),
                    sender = event.sender,
                    body = kind.body,
                    msgType = "Sticker",
                    isEdited = false,
                    isAvailable = true
                )
            }

            is MsgLikeKind.Poll -> {
                MessageState(
                    id = event.eventOrTransactionId.toString(),
                    sender = event.sender,
                    body = kind.question,
                    msgType = "Poll",
                    isEdited = kind.hasBeenEdited,
                    isAvailable = true
                )
            }

            is MsgLikeKind.UnableToDecrypt -> {
                MessageState(
                    id = event.eventOrTransactionId.toString(),
                    sender = event.sender,
                    body = "消息无法解密",
                    msgType = "UnableToDecrypt",
                    isEdited = false,
                    isAvailable = false
                )
            }

            MsgLikeKind.Redacted -> {
                MessageState(
                    id = event.eventOrTransactionId.toString(),
                    sender = event.sender,
                    body = "消息已撤回或内容已清除",
                    msgType = "Redacted",
                    isEdited = false,
                    isAvailable = false
                )
            }

            is MsgLikeKind.LiveLocation -> {
                MessageState(
                    id = event.eventOrTransactionId.toString(),
                    sender = event.sender,
                    body = "实时位置消息",
                    msgType = "LiveLocation",
                    isEdited = false,
                    isAvailable = true
                )
            }

            is MsgLikeKind.Other -> {
                MessageState(
                    id = event.eventOrTransactionId.toString(),
                    sender = event.sender,
                    body = "",
                    msgType = "Other",
                    isEdited = false,
                    isAvailable = true
                )
            }
        }
    }

    suspend fun sendMessage(roomId: String, message: String) {
        val room = client.getRoom(roomId)
        val jsonMessage = com.timcoumu.core.matrix.json.MessageContent(
            msgtype = "m.text",
            body = message
        )
        room?.sendRaw(
            eventType = "m.room.message",
            content = Gson().toJson(
                jsonMessage
            )
        )
//        registerTimelineForOneRoom(roomId)
    }

    suspend fun createRoomOrSpace(
        name: String,
        topic: String,
        type: RoomType,
        preset: MyRoomPreset
    ) {
        val parameters = CreateRoomParameters(
            avatar = null,
            name = name,
            topic = topic,
            isSpace = type == RoomType.SPACE,
            isEncrypted = true,
            visibility = RoomVisibility.Private,
            preset = preset.toRoomPreset()
        )
        client.createRoom(parameters)
    }

    fun getRoomState(): RoomState? {
        return currentRoom?.toRoomState()
    }
    suspend fun getRoomMembers(){
        val iterator: RoomMembersIterator? =  currentRoom?.members()
        val roomMembers:List<RoomMember> = iterator?.nextChunk(100U)?:emptyList()
        roomMembersFlow.value = roomMembers.map { it.toMyRoomMemberState() }
        iterator?.close()
    }
    suspend fun leaveRoom(){
        currentRoom?.leave()
    }
    suspend fun setRoomName(name: String){
        currentRoom?.setName(name)
    }
    suspend fun setRoomTopic(topic: String){
        currentRoom?.setTopic(topic)
    }
}