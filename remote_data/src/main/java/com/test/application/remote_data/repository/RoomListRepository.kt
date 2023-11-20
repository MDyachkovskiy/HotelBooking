package com.test.application.remote_data.repository

interface RoomListRepository<T> {
    suspend fun getData(): T
}