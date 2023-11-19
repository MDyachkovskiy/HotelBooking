package com.test.application.remote_data.dto

data class AboutTheHotelDTO(
    val description: String = "",
    val peculiarities: List<String> = listOf()
)