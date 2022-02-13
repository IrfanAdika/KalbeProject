package com.kalbe.datasource.remote

import com.google.gson.JsonObject

class ApiRepository(private val services: Services): BaseRepository() {

    suspend fun register(body: JsonObject) = safeApiCall {
        services.postRegister(body = body)
    }

    suspend fun login(body: JsonObject) = safeApiCall {
        services.postLogin(body = body)
    }
}