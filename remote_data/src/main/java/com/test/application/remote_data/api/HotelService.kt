package com.test.application.remote_data.api

import com.test.application.remote_data.dto.HotelDTO
import retrofit2.http.GET

interface HotelService {
    @GET("v3/d144777c-a67f-4e35-867a-cacc3b827473")
    suspend fun getHotelInfo(): HotelDTO
}