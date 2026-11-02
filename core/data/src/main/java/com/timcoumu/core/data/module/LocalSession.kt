package com.timcoumu.core.data.module

data class LocalSession(
    val accessToken: String,
    val refreshToken: String,
    val userId: String,
    val homeserverUrl: String,
    val slidingSyncVersion: String,
    val oauthData: String,
    val deviceId: String
)
