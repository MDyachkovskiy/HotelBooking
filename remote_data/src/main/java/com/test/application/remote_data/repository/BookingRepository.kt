package com.test.application.remote_data.repository

interface BookingRepository<T> {
    suspend fun getData(): T
}