package com.test.application.remote_data.api

import com.test.application.remote_data.dto.RoomListDTO
import retrofit2.http.GET

interface RoomListService {
    @GET("v3/8b532701-709e-4194-a41c-1a903af00195")
    suspend fun getRoomsInfo(): RoomListDTO
}