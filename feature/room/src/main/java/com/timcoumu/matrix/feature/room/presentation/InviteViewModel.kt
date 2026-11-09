package com.timcoumu.matrix.feature.room.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.timcoumu.core.matrix.MatrixRoomService
import com.timcoumu.core.matrix.MatrixService
import com.timcoumu.core.matrix.model.UserState
import com.timcoumu.matrix.feature.room.effect.InviteEffect
import com.timcoumu.matrix.feature.room.state.ConfirmDialogState
import com.timcoumu.matrix.feature.room.state.InviteUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InviteViewModel @Inject constructor(): ViewModel() {
    private val TAG = "InviteViewModel"
    private val _inviteEffect = MutableSharedFlow<InviteEffect>(replay = 0)
    private val _inviteUiState = MutableStateFlow<InviteUiState>(
        InviteUiState(
            username = "",
            userList = emptyList(),
            roomId = null,
            confirmDialogState = ConfirmDialogState(
                showDialog = false,
                user = null
            )
        )
    )
    val inviteUiState = _inviteUiState.asStateFlow()
    val inviteEffect = _inviteEffect.asSharedFlow()
    fun onChangeUsername(username: String){
        _inviteUiState.update {
            it.copy(
                username = username
            )
        }
    }
    fun onSearchUsers(){
        viewModelScope.launch {
            val users =  MatrixService.searchUser(_inviteUiState.value.username)
            _inviteUiState.update {
                it.copy(
                    userList = users
                )
            }
        }
    }
    fun inviteUser(){
        viewModelScope.launch {
            try {
                val roomId = _inviteUiState.value.roomId
                val userId = _inviteUiState.value.confirmDialogState.user?.userId
                if (roomId.isNullOrBlank()){
                    throw Exception("roomId isNullOrBlank()")
                }
                if (userId.isNullOrBlank()){
                    throw Exception("userId isNullOrBlank()")
                }
                if (!roomId.isNullOrBlank() && !userId.isNullOrBlank()) MatrixRoomService.inviteUser(roomId,userId)
                _inviteEffect.emit(InviteEffect.InviteUser(true,"已邀请"))
            }catch (e: CancellationException){
                throw e
            }catch (e: Exception){
                Log.e(TAG, "inviteUser failure: ${e.message}")
                _inviteEffect.emit(InviteEffect.InviteUser(false,"邀请失败，请检查是否拥有相关权限"))
            }
        }
    }
    fun initInviteViewModel(roomId: String?){
        _inviteUiState.update {
            it.copy(
                roomId = roomId
            )
        }
    }
    fun onDismissDialog(){
        _inviteUiState.update {
            it.copy(
                confirmDialogState = ConfirmDialogState(
                    showDialog = false,
                    user = null,
                )
            )
        }
    }
    fun onShowDialog(user: UserState){
        _inviteUiState.update {
            it.copy(
                confirmDialogState = ConfirmDialogState(
                    showDialog = true,
                    user = user,
                )
            )
        }
    }
}