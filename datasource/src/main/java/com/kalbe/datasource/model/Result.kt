package com.kalbe.datasource.model


sealed class Result<out T> {
    data class Success<out T>(val value : T, val  kind: String = "") : Result<T>()
    data class Failure (val message: String = "", val kind: String = "")
    : Result<Nothing>()
}
