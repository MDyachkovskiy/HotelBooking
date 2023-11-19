package com.test.application.core.interactor

import com.test.application.core.domain.Room
import com.test.application.remote_data.repository.Repository

class RoomListInteractorImpl(
    private val repository: Repository<List<Room>>
) : RoomListInteractor {
    override suspend fun getRoomsInfo(): List<Room> {
        return repository.getData()
    }
}