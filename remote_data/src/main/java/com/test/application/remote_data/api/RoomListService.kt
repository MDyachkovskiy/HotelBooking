package com.test.application.remote_data.api

import com.test.application.remote_data.dto.RoomListDTO
import com.test.application.remote_data.utils.ROOMS_URL
import retrofit2.http.GET

interface RoomListService {
    @GET(ROOMS_URL)
    suspend fun getRoomsInfo(): RoomListDTO
}