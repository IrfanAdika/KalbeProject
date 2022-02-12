package com.kalbe.datasource.remote

import com.google.gson.JsonObject
import com.kalbe.datasource.model.Auth
import com.kalbe.datasource.model.User
import retrofit2.http.Body
import retrofit2.http.POST

interface Services {

    @POST("register")
    suspend fun postRegister(@Body body: JsonObject): Response<User>

    @POST("auth/login")
    suspend fun postLogin(@Body body: JsonObject): Auth
}