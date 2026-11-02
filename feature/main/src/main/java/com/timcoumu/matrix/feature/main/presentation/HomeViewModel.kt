package com.timcoumu.matrix.feature.main.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.timcoumu.core.matrix.MatrixRoomService
import com.timcoumu.core.matrix.MatrixService
import com.timcoumu.matrix.feature.main.state.HomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(): ViewModel() {
    private val _isAddDialogVisible = MutableStateFlow(false)
    val homeUiState =
        combine(
            MatrixRoomService.roomsFlow,
            _isAddDialogVisible
        ){
            rooms,isAddDialogVisible->
            HomeUiState(
                rooms = rooms,
                isAddDialogVisible = isAddDialogVisible
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HomeUiState(
                rooms = emptyList(),
                isAddDialogVisible = false
            )
        )
    fun onShowAddDialogVisible(){
        _isAddDialogVisible.value = true
    }
    fun onHideAddDialogVisible(){
        _isAddDialogVisible.value = false
    }

    fun initRoomService(){
        viewModelScope.launch {
            MatrixRoomService.roomsFlow
        }
    }
}