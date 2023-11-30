package com.test.application.remote_data.api

import com.test.application.core.utilities.HOTEL_URL
import com.test.application.remote_data.dto.HotelDTO
import retrofit2.http.GET

interface HotelService {
    @GET(HOTEL_URL)
    suspend fun getHotelInfo(): HotelDTO
}