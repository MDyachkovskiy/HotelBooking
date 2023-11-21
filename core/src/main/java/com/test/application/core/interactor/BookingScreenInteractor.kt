package com.test.application.core.interactor

import com.test.application.core.domain.Booking
import com.test.application.core.utilities.AppState

interface BookingScreenInteractor : Interactor<AppState> {
    suspend fun getBookingInfo(): Booking
}