package com.timcoumu.core.matrix.model

import org.matrix.rustcomponents.sdk.Room
import org.matrix.rustcomponents.sdk.SpaceRoom


data class RoomState(
    val id: String,
    val topic: String?,
    val displayName: String?,
    val avatarUrl: String?,
)
 fun RoomtoRoomState(
    room: Room
): RoomState {
    return RoomState(
        id = room.id(),
        topic = room.topic(),
        displayName = room.displayName(),
        avatarUrl = room.avatarUrl()
    )
}
fun Room.toRoomState(): RoomState {
    return RoomState(
        id = id(),
        topic = topic(),
        displayName = displayName(),
        avatarUrl = avatarUrl()
    )
}
//特殊类型，Space下的SpaceRoom
fun SpaceRoom.toRoom(): RoomState{
    return RoomState(
        id = this.roomId,
        topic = this.topic,
        displayName = this.displayName,
        avatarUrl = this.avatarUrl
    )
}