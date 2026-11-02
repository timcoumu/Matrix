package com.timcoumu.core.matrix.model

import kotlinx.serialization.Serializable
import org.matrix.rustcomponents.sdk.JoinRule
import org.matrix.rustcomponents.sdk.SpaceRoom

@Serializable
data class SpaceState(
    val id:String,
    val displayName:String,
    val topic:String?,
    val avatarUrl:String?,
    val joinRule:String
)
fun SpaceRoom.toSpaceState():SpaceState{
    return SpaceState(
        id = roomId,
        displayName = displayName,
        topic = topic,
        avatarUrl = avatarUrl,
        joinRule = when (this) {
            JoinRule.Custom -> "Custom"
            JoinRule.Invite -> "Invite"
            JoinRule.Knock -> "Knock"
            JoinRule.KnockRestricted -> "KnockRestricted"
            JoinRule.Private -> "Private"
            JoinRule.Public -> "Public"
            JoinRule.Restricted -> "Restricted"
            else -> "Unknow"
        }
    )
}