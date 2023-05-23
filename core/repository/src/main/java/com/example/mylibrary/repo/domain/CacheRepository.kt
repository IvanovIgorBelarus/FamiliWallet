package com.example.mylibrary.repo.domain

interface CacheRepository<T> {
    suspend fun put(collectionName: String, cache: T)
    suspend fun get(collectionName: String): T?
    suspend fun clear(collectionName: String)
    suspend fun isEmpty(collectionName: String): Boolean
}