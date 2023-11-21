package com.test.application.core.repository

import com.test.application.core.domain.Booking
import com.test.application.core.mapper.mapFromBookingDTOtoDomain
import com.test.application.remote_data.api.BookingService
import com.test.application.remote_data.repository.BookingRepository

class BookingRepositoryImpl(
    private val bookingService: BookingService
) : BookingRepository<Booking> {

    override suspend fun getData(): Booking {
        val bookingDTO = bookingService.getBookingInfo()
        return mapFromBookingDTOtoDomain(bookingDTO)
    }
}