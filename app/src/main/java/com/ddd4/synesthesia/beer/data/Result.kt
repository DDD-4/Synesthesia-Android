package com.ddd4.synesthesia.beer.data

sealed class Result<T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Error<T>(val e: Throwable) : Result<T>()
}