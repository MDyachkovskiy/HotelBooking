package com.test.application.remote_data.repository

import com.test.application.core.domain.Booking
import com.test.application.remote_data.mapper.mapFromBookingDTOtoDomain
import com.test.application.remote_data.api.BookingService
import com.test.application.core.repository.BookingRepository

class BookingRepositoryImpl(
    private val bookingService: BookingService
) : BookingRepository<Booking> {

    override suspend fun getData(): Booking {
        val bookingDTO = bookingService.getBookingInfo()
        return mapFromBookingDTOtoDomain(bookingDTO)
    }
}