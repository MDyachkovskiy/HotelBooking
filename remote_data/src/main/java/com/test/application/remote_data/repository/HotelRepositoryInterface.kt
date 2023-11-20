package com.test.application.remote_data.repository

interface HotelRepositoryInterface<T> {
    suspend fun getData(): T
}