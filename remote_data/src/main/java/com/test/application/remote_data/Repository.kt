package com.test.application.remote_data

interface Repository<T> {
    suspend fun getData(): T
}