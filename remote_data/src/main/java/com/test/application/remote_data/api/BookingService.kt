package com.test.application.remote_data.api

import com.test.application.remote_data.dto.BookingDTO
import retrofit2.http.GET

interface BookingService {
    @GET("v3/63866c74-d593-432c-af8e-f279d1a8d2ff")
    suspend fun getBookingInfo(): BookingDTO
}