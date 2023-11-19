package com.test.application.remote_data.repository

interface Repository<T> {
    suspend fun getData(): T
}