package com.test.application.core.domain

data class Hotel (
    val id: Int,
    val name: String,
    val address: String,
    val minimalPrice: Long?,
    val priceForIt: String?,
    val rating: Double?,
    val ratingName: String?,
    val imageUrls: List<String>?,
    val aboutTheHotel: AboutTheHotel?
)