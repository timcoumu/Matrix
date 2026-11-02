package com.timcoumu.matrix.core.network.api

import com.timcoumu.matrix.core.network.dto.CheckNameResponse
import com.timcoumu.matrix.core.network.dto.RegisterBody
import com.timcoumu.matrix.core.network.dto.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface MatrixApi {
    @GET("/_matrix/client/v3/register/available")
    suspend fun checkName(
        @Query("username") userName:String
    ): Response<CheckNameResponse>
    @POST("/_matrix/client/v3/register")
    suspend fun register(
        @Query("kind") kind: String? = "user",
        @Body body: RegisterBody
    ): Response<RegisterResponse>
}