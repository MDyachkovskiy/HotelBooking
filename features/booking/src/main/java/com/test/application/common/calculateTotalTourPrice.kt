package com.test.application.common

import com.test.application.core.domain.Booking

fun calculateTotalTourPrice(data: Booking): Int {
    return data.tourPrice + (data.fuelCharge ?: 0) + (data.serviceCharge ?: 0)
}