package com.example.familiwallet.core.repository.domain

interface CacheRepository <T> {
    suspend fun put(cache: T)
    suspend fun get(): T?
    suspend fun clear()
    suspend fun isEmpty(): Boolean
}