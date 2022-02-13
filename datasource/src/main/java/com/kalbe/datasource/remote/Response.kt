package com.kalbe.datasource.remote

import com.google.gson.annotations.SerializedName

/**
 * Success response
 * from server
 */
data class Response<out T> (
    val success: Boolean,
    val code: Int,
    val message: String,
    val data: T,
    val meta: Meta
)

data class Meta (
    @SerializedName("count")
    var count: Int,

    @SerializedName("page")
    var page: Int
)

/**
 * Error response
 * from server
 */
data class ErrorResponse (
    var code: Int,
    var success: Boolean = false,
    var message: String,
    var data: Any? = null,
    var stack: Any? = null
)