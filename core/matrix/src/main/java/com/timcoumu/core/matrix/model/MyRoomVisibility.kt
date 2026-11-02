package com.timcoumu.core.matrix.model

import org.matrix.rustcomponents.sdk.RoomVisibility

sealed class MyRoomVisibility {
    object PRIVATE: MyRoomVisibility()
    object PUBLIC: MyRoomVisibility()
}
fun MyRoomVisibility.toRoomVisibility(): RoomVisibility{
    return when(this){
        is MyRoomVisibility.PUBLIC -> RoomVisibility.Public
        is MyRoomVisibility.PRIVATE -> RoomVisibility.Private
    }
}