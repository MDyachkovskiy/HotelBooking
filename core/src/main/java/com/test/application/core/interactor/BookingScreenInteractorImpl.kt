package com.test.application.core.interactor

import com.test.application.core.domain.Booking
import com.test.application.remote_data.repository.BookingRepository

class BookingScreenInteractorImpl(
    private val repositoryRemote: BookingRepository<Booking>
) : BookingScreenInteractor {
    override suspend fun getBookingInfo(): Booking {
        return repositoryRemote.getData()
    }
}