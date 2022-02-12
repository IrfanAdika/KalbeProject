package com.kalbe.datasource.remote

sealed class Resource<out T> {
    data class Success<out T>(val value : T) : Resource<T>()
    data class Failure  (
        val errorMessage : String = ""
    ): Resource<Nothing>()
}