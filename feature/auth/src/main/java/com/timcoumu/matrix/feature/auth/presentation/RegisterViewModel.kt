package com.timcoumu.matrix.feature.auth.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.timcoumu.matrix.feature.auth.data.RepositoryImpl
import com.timcoumu.matrix.feature.auth.effect.RegisterEffect
import com.timcoumu.matrix.feature.auth.state.RegisterUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repository: RepositoryImpl
) : ViewModel(){
    private val TAG = "RegisterViewModel"
    private val _registerEffect = MutableSharedFlow<RegisterEffect>(0)
    val registerEffect = _registerEffect.asSharedFlow()
    private val _registerUiState = MutableStateFlow(
        RegisterUiState(
            username = "",
            password = "",
            confirmPassword = "",
            registerToken = "ChangeMeToSomethingSecret123"
        )
    )
    val registerUiState = _registerUiState.asStateFlow()
    fun onUsernameChanged(value:String){
        _registerUiState.update {
            it.copy(
                username = value
            )
        }
    }
    fun onPasswordChanged(value:String){
        _registerUiState.update {
            it.copy(
                password = value
            )
        }
    }
    fun onConfirmPasswordChanged(value:String){
        _registerUiState.update {
            it.copy(
                confirmPassword = value
            )
        }
    }
    fun onRegisterTokenChanged(value:String){
        _registerUiState.update {
            it.copy(
                registerToken = value
            )
        }
    }
    fun onRegister(){
        viewModelScope.launch {
            try {
                if (_registerUiState.value.password != _registerUiState.value.confirmPassword) throw Exception("两次输入的密码不一样")
                if (!repository.checkNameAvailable(_registerUiState.value.username)) throw Exception("用户名不可用")
                val result = repository.register(
                    _registerUiState.value.username,
                    _registerUiState.value.password,
                    registerToken = _registerUiState.value.registerToken
                )
                if (result) {
                    _registerEffect.emit(
                        RegisterEffect.Register(
                            success = true,
                            message = "注册成功"
                        )
                    )
                }else{
                    _registerEffect.emit(
                        RegisterEffect.Register(
                            success = false,
                            message = "注册失败"
                        )
                    )
                }
            }catch (e: CancellationException){
                throw e
            }catch (e: Exception){
                _registerEffect.emit(
                    RegisterEffect.Register(
                        success = false,
                        message = "注册失败 ${e}"
                    )
                )
                Log.d(TAG, "onRegister: ${e}")
            }

        }
    }
}