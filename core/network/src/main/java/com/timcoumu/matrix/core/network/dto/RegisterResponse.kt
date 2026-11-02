package com.timcoumu.matrix.core.network.dto

import com.google.gson.annotations.SerializedName

data class RegisterResponse(
    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("user_id")
    val userId: String,
    @SerializedName("device_id")
    val deviceId: String,
    @SerializedName("home_server")
    val homeServer: String
)
