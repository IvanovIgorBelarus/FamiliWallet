package com.example.familiwallet.core.repository.domain

interface CacheRepository <T> {
    fun put(cache: T)
    fun get(): T?
    fun clear()
    fun isEmpty(): Boolean
}