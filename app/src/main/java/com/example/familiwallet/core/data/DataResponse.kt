package com.example.familiwallet.core.data


sealed class DataResponse<out T> {
    class Success<T>(val data: T) : DataResponse<T>()

    class Error(val exception: Throwable) : DataResponse<Nothing>()
}
