package com.test.application.remote_data.api

import com.test.application.core.utilities.BOOKING_URL
import com.test.application.remote_data.dto.BookingDTO
import retrofit2.http.GET

interface BookingService {
    @GET(BOOKING_URL)
    suspend fun getBookingInfo(): BookingDTO
}