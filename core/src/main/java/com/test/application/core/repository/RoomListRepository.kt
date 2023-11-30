package com.test.application.core.repository

interface RoomListRepository<T> {
    suspend fun getData(): T
}