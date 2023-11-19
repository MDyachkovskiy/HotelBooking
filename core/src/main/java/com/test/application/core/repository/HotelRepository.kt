package com.test.application.core.repository

import com.test.application.core.domain.Hotel
import com.test.application.core.mapper.mapHotelDTOToDomain
import com.test.application.remote_data.api.HotelService
import com.test.application.remote_data.repository.Repository

class HotelRepository(
    private val hotelService: HotelService
) : Repository<Hotel> {
    override suspend fun getData(): Hotel {
        val hotelDTO = hotelService.getHotelInfo()
        return mapHotelDTOToDomain(hotelDTO)
    }
}