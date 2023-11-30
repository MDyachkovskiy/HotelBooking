package com.test.application.remote_data.api

import com.test.application.core.utilities.ROOMS_URL
import com.test.application.remote_data.dto.RoomListDTO
import retrofit2.http.GET

interface RoomListService {
    @GET(ROOMS_URL)
    suspend fun getRoomsInfo(): RoomListDTO
}