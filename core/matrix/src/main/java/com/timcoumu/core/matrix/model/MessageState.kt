package com.timcoumu.core.matrix.model

data class MessageState(
    val id:String,
    val isAvailable: Boolean,
    val sender: String,
    val body: String,
    val msgType: String,
    val isEdited: Boolean,
)