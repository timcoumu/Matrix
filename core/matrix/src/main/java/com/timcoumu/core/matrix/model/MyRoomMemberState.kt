package com.timcoumu.core.matrix.model

import org.matrix.rustcomponents.sdk.MembershipState
import org.matrix.rustcomponents.sdk.RoomMember
import uniffi.matrix_sdk.RoomMemberRole

data class MyRoomMemberState(
    val usrId: String,
    val avatarUrl: String,
    val displayName: String,
    val memberRole: String,
    val memberShip: String
)

fun RoomMember.toMyRoomMemberState(): MyRoomMemberState {
    return MyRoomMemberState(
        usrId = userId,
        avatarUrl = avatarUrl ?: "",
        displayName = displayName ?: "",
        memberRole = when (suggestedRoleForPowerLevel) {
            RoomMemberRole.USER -> "USER"
            RoomMemberRole.ADMINISTRATOR -> "ADMINISTRATOR"
            RoomMemberRole.MODERATOR -> "MODERATOR"
            RoomMemberRole.CREATOR -> "CREATOR"
        },
        memberShip = when (membership) {
            MembershipState.Join -> "Join"
            MembershipState.Ban -> "Ban"
            MembershipState.Invite -> "Invite"
            MembershipState.Knock -> "Knock"
            MembershipState.Leave -> "Leave"
            else -> "Unknow"
        }
    )
}