package com.timcoumu.core.matrix

import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.Test

class LoginTest {
    @Test
    fun `LOGIN_TEST`(): Unit = runBlocking {
        val userId = Matrix.loginWithPassword(
            username = "admin",
            password = "matrix.2021@gxy",
            initialDeviceName = "test",
            deviceId = "test"
        ){accessToken, refreshToken, userId, homeserverUrl, oAuthData, deviceId ->

        }
        assertTrue("login failure,user id:${userId}",userId.isNotBlank())
    }
}