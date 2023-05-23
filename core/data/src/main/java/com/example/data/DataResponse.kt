package com.example.data


sealed class DataResponse<out T> {
    class Success<T>(val data: T) : DataResponse<T>()

    class Error(val exception: Throwable) : DataResponse<Nothing>()

    fun getValueOrNull() = (this as? Success)?.data
}
