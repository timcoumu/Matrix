package com.timcoumu.matrix.feature.room.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.timcoumu.core.matrix.MatrixRoomService
import com.timcoumu.matrix.feature.room.effect.InviteBoxEffect
import com.timcoumu.matrix.feature.room.state.InviteBoxUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InviteBoxViewModel @Inject constructor(): ViewModel() {
    private val TAG = "InviteBoxViewModel"
    private val _inviteBoxEffect = MutableSharedFlow<InviteBoxEffect>(replay = 0)
    val inviteBoxEffect = _inviteBoxEffect.asSharedFlow()
    val inviteBoxUiState = combine(
        MatrixRoomService.inviteRoomsFlow
    ){
        InviteBoxUiState(
            inviteList = it.get(0)
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = InviteBoxUiState(
            inviteList = emptyList()
        )
    )
    fun acceptInvite(roomId: String){
        viewModelScope.launch {
            viewModelScope.launch {
                try {
                    MatrixRoomService.acceptInvite(roomId)
                    _inviteBoxEffect.emit(InviteBoxEffect.AcceptEffect(
                        result = true,
                        message = "已加入"
                    ))
                }catch (e: CancellationException){
                    throw e
                }catch (e: Exception){
                    Log.e(TAG, "acceptInvite: ${e.message}", )
                    _inviteBoxEffect.emit(InviteBoxEffect.AcceptEffect(
                        result = false,
                        message = "加入失败"
                    ))
                }
            }
        }
    }
    fun rejectInvite(roomId: String){
        viewModelScope.launch {
            try {
                MatrixRoomService.rejectInvite(roomId)
                _inviteBoxEffect.emit(InviteBoxEffect.RejectEffect(
                    result = true,
                    message = "已拒绝"
                ))
            }catch (e: CancellationException){
                throw e
            }catch (e: Exception){
                Log.e(TAG, "rejectInvite: ${e.message}", )
                _inviteBoxEffect.emit(InviteBoxEffect.RejectEffect(
                    result = false,
                    message = "拒绝失败"
                ))
            }
        }
    }
}