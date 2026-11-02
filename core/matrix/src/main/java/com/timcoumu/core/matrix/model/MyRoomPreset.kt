package com.timcoumu.core.matrix.model

import org.matrix.rustcomponents.sdk.RoomPreset

sealed class MyRoomPreset {
    object PRIVATE_CHAT: MyRoomPreset()
    object PUBLIC_CHAT: MyRoomPreset()
//    object TRUSTED_PRIVATE_CHAT: MyRoomPreset()
}

fun MyRoomPreset.toRoomPreset():RoomPreset{
    return when(this){
        is MyRoomPreset.PRIVATE_CHAT -> RoomPreset.PRIVATE_CHAT
        is MyRoomPreset.PUBLIC_CHAT-> RoomPreset.PUBLIC_CHAT
//        is MyRoomPreset.TRUSTED_PRIVATE_CHAT -> RoomPreset.TRUSTED_PRIVATE_CHAT
    }
}