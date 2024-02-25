package com.test.application.remote_data.api

import com.test.application.remote_data.dto.HotelDTO
import com.test.application.remote_data.utils.HOTEL_URL
import retrofit2.http.GET

interface HotelService {
    @GET(HOTEL_URL)
    suspend fun getHotelInfo(): HotelDTO
}