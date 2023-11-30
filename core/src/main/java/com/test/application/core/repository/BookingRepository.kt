package com.test.application.core.repository

interface BookingRepository<T> {
    suspend fun getData(): T
}