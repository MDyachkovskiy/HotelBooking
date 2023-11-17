package com.test.application.core.domain

data class Room (
    val id: Int,
    val name: String,
    val price: Long?,
    val pricePer: String?,
    val peculiarities: List<String>?,
    val imageUrls: List<String>?
)