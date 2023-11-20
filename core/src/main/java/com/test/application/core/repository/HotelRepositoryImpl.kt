package com.test.application.core.repository

import com.test.application.core.domain.Hotel
import com.test.application.core.mapper.mapHotelDTOToDomain
import com.test.application.remote_data.api.HotelService
import com.test.application.remote_data.repository.HotelRepository

class HotelRepositoryImpl(
    private val hotelService: HotelService
) : HotelRepository<Hotel> {
    override suspend fun getData(): Hotel {
        val hotelDTO = hotelService.getHotelInfo()
        val hotel = mapHotelDTOToDomain(hotelDTO)
        return hotel
    }
}