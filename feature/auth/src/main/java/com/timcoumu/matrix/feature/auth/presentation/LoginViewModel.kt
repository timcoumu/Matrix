package com.timcoumu.matrix.feature.auth.presentation

import android.app.Application
import android.os.Build
import android.provider.Settings
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.timcoumu.core.matrix.Matrix
import com.timcoumu.matrix.feature.auth.data.RepositoryImpl
import com.timcoumu.matrix.feature.auth.effect.LoginEffect
import com.timcoumu.matrix.feature.auth.state.LoginUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.coroutines.cancellation.CancellationException

@HiltViewModel
class LoginViewModel @Inject constructor(
    application: Application,
    private val repository: RepositoryImpl
) : AndroidViewModel(application) {
    private val TAG = "LoginViewModel"
    private val _username = MutableStateFlow("")
    private val _password = MutableStateFlow("")
    private val _deviceId = Settings.Secure.getString(
        application.contentResolver,
        Settings.Secure.ANDROID_ID
    ) ?: ""
    private val _initialDeviceName = "${Build.MANUFACTURER} ${Build.MODEL}"
    private val _loginEffect = MutableSharedFlow<LoginEffect>(replay = 0)
    val loginEffect = _loginEffect.asSharedFlow()
    val loginUiState = combine(_username, _password) { username, password ->
        LoginUiState(
            username = username,
            password = password
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = LoginUiState(
            username = "",
            password = ""
        )
    )

    fun onUsernameChange(value: String) {
        _username.value = value
    }

    fun onPasswordChange(value: String) {
        _password.value = value
    }

    fun login() {
        viewModelScope.launch {
            var message: String?=null
            val result = try {
                Matrix.loginWithPassword(
                    username = _username.value,
                    password = _password.value,
                    initialDeviceName = _initialDeviceName,
                    deviceId = _deviceId
                ) { accessToken, refreshToken, userId, homeserverUrl, oAuthData, deviceId, slidingSyncVersion ->
                    viewModelScope.launch {
                        repository.login(
                            accessToken = accessToken,
                            refreshToken = refreshToken,
                            userId = userId,
                            homeserverUrl = homeserverUrl,
                            deviceId = deviceId,
                            oauthData = oAuthData,
                            slidingSyncVersion = slidingSyncVersion,
                        )
                    }
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                message=e.message
                Log.e(TAG, "login: ${e}")
                false
            }
            _loginEffect.emit(LoginEffect.Login(
                success = result,
                message = message
            ))
        }
    }
}