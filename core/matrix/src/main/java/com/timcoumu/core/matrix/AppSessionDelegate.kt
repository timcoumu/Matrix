package com.timcoumu.core.matrix

import android.util.Log
import com.timcoumu.core.data.AppDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.matrix.rustcomponents.sdk.ClientSessionDelegate
import org.matrix.rustcomponents.sdk.Session
import org.matrix.rustcomponents.sdk.SlidingSyncVersion

class AppSessionDelegate(private val dataStore: AppDataStore): ClientSessionDelegate {
    override fun retrieveSessionFromKeychain(userId: String): Session {
        Log.d("retrieveSessionFromKeychain", "userId: ${userId}")
        val cache = dataStore.loadSession()
        return Session(
            accessToken = cache.accessToken,
            refreshToken = cache.refreshToken.let { it.ifBlank { null } },
            userId = cache.userId,
            deviceId = cache.deviceId,
            homeserverUrl = cache.homeserverUrl,
            oauthData = cache.oauthData.let { it.ifBlank { null } },
            slidingSyncVersion = if (cache.slidingSyncVersion == "NATIVE") SlidingSyncVersion.NATIVE else SlidingSyncVersion.NONE
        )
    }

    override fun saveSessionInKeychain(session: Session) {
        CoroutineScope(Dispatchers.IO).launch {
            dataStore.saveSession(
                accessToken = session.userId,
                refreshToken = session.refreshToken?:"",
                userId = session.userId,
                homeserverUrl = session.homeserverUrl,
                slidingSyncVersion = session.slidingSyncVersion.name,
                oauthData = session.oauthData?:"",
                deviceId = session.deviceId
            )
        }
    }
}