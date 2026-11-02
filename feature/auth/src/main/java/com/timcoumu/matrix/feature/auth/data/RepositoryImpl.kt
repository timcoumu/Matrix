package com.timcoumu.matrix.feature.auth.data

import android.util.Log
import com.timcoumu.core.data.AppDataStore
import com.timcoumu.matrix.core.network.NetworkClient
import com.timcoumu.matrix.core.network.api.MatrixApi
import com.timcoumu.matrix.core.network.dto.Auth
import com.timcoumu.matrix.core.network.dto.RegisterBody
import kotlinx.coroutines.CancellationException
import javax.inject.Inject


interface Repository {
    suspend fun login(
        accessToken: String,
        refreshToken: String,
        userId: String,
        homeserverUrl: String,
        oAuthData: String,
        deviceId: String,
        slidingSyncVersion: String
    )

    suspend fun checkNameAvailable(username: String): Boolean
    suspend fun register(
        username: String,
        password: String,
        registerToken: String?
    ): Boolean
}

class RepositoryImpl @Inject constructor(private val dataStore: AppDataStore) : Repository {
    private val TAG = "RepositoryImpl"
    val matrixApi = NetworkClient.provideApi(MatrixApi::class.java)
    override suspend fun login(
        accessToken: String,
        refreshToken: String,
        userId: String,
        homeserverUrl: String,
        deviceId: String,
        oauthData: String,
        slidingSyncVersion: String
    ) {
        dataStore.saveSession(
            accessToken = accessToken,
            refreshToken = refreshToken,
            userId = userId,
            homeserverUrl = homeserverUrl,
            oauthData = oauthData,
            deviceId = deviceId,
            slidingSyncVersion = slidingSyncVersion
        )
    }

    override suspend fun checkNameAvailable(username: String): Boolean {
        return try {
            val response = matrixApi.checkName(username)
            Log.d(TAG, "checkNameAvailable response: ${response}")
            response.isSuccessful
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Log.e(TAG, "checkNameAvailable: ${e}")
            false
        }
    }

    override suspend fun register(
        username: String,
        password: String,
        registerToken: String?
    ): Boolean {
        val response = matrixApi.register(
            body = RegisterBody(
                username = username,
                password = password,
                auth = Auth(
                    type = "m.login.registration_token",
                    token = registerToken
                )
            )
        )
        Log.d(TAG, "register response body: ${response}")
        return response.isSuccessful
    }
}