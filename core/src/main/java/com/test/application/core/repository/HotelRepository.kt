package com.test.application.core.repository

interface HotelRepository<T> {
    suspend fun getData(): T
}