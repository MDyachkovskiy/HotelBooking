package com.test.application.remote_data.dto

data class HotelDTO(
    val about_the_hotel: AboutTheHotelDTO = AboutTheHotelDTO(),
    val adress: String = "",
    val id: Int = 0,
    val image_urls: List<String> = listOf(),
    val minimal_price: Int = 0,
    val name: String = "",
    val price_for_it: String = "",
    val rating: Double = 0.0,
    val rating_name: String = ""
)