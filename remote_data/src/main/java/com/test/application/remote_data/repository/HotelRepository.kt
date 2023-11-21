package com.test.application.remote_data.repository

interface HotelRepository<T> {
    suspend fun getData(): T
}