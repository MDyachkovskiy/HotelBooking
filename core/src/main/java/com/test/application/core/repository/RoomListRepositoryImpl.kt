package com.test.application.core.repository

import com.test.application.core.domain.Room
import com.test.application.core.mapper.mapRoomDTOToRoom
import com.test.application.remote_data.api.RoomListService
import com.test.application.remote_data.repository.RoomListRepository

class RoomListRepositoryImpl(
    private val roomsService: RoomListService
) : RoomListRepository<List<Room>> {

    override suspend fun getData(): List<Room> {
        val roomListDTO = roomsService.getRoomsInfo()
        return roomListDTO.rooms.map {roomDTO ->
            mapRoomDTOToRoom(roomDTO)
        }
    }
}