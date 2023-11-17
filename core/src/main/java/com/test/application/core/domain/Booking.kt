package com.test.application.core.domain

data class Booking(
    val id: Int,
    val hotelName: String,
    val hotelAddress: String?,
    val hotelRating: Double?,
    val ratingName: String?,
    val departure: String,
    val arrivalCountry: String,
    val tourDateStart: String,
    val tourDateStop: String,
    val numberOfNights: Int,
    val room: String,
    val nutrition: String?,
    val tourPrice: Long,
    val tourCharge: Long,
    val fuelCharge: Long?,
    val serviceCharge: Long?
)
