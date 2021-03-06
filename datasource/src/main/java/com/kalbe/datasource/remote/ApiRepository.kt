package com.kalbe.datasource.remote

import com.google.gson.JsonObject

class ApiRepository(private val services: Services): BaseRepository() {

    suspend fun register(body: JsonObject) = safeApiCall {
        services.postRegister(body = body)
    }

    suspend fun login(body: JsonObject) = safeApiCall {
        services.postLogin(body = body)
    }

    suspend fun addProduct(body: JsonObject) = safeApiCall {
        services.postProduct(body = body)
    }

    suspend fun editProduct(body: JsonObject) = safeApiCall {
        services.postEdit(body = body)
    }

    suspend fun getProducts() = safeApiCall {
        services.getProducts()
    }

    suspend fun deleteProduct(body: JsonObject) = safeApiCall {
        services.deleteProduct(body = body)
    }

    suspend fun getProductBySku(body: JsonObject) = safeApiCall {
        services.getProductBySku(body = body)
    }
}