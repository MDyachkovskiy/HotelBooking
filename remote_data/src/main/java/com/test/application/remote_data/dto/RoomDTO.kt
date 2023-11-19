package com.test.application.remote_data.dto

data class RoomDTO(
    val id: Int = 0,
    val image_urls: List<String> = listOf(),
    val name: String = "",
    val peculiarities: List<String> = listOf(),
    val price: Int = 0,
    val price_per: String = ""
)