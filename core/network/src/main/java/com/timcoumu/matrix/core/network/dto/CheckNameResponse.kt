package com.timcoumu.matrix.core.network.dto

import com.google.gson.annotations.SerializedName

data class CheckNameResponse(
    @SerializedName("available")
    val available: Boolean
)