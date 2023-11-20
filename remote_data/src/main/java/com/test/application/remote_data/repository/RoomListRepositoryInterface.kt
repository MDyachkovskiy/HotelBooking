package com.test.application.remote_data.repository

interface RoomListRepositoryInterface<T> {
    suspend fun getData(): T
}