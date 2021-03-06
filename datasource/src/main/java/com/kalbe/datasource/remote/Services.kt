package com.kalbe.datasource.remote

import com.google.gson.JsonObject
import com.kalbe.datasource.model.Auth
import com.kalbe.datasource.model.Product
import com.kalbe.datasource.model.User
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface Services {

    @POST("register")
    suspend fun postRegister(@Body body: JsonObject): Response<User>

    @POST("auth/login")
    suspend fun postLogin(@Body body: JsonObject): Auth

    @POST("item/add")
    suspend fun postProduct(@Body body: JsonObject): Product

    @POST("item/update")
    suspend fun postEdit(@Body body: JsonObject): Product

    @GET("items")
    suspend fun getProducts(): List<Product>

    @POST("item/delete")
    suspend fun deleteProduct(@Body body: JsonObject): Product

    @POST("item/search")
    suspend fun getProductBySku(@Body body: JsonObject): Product
}