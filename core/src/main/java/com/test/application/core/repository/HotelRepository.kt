package com.test.application.core.repository

import android.util.Log
import com.test.application.core.domain.Hotel
import com.test.application.core.mapper.mapHotelDTOToDomain
import com.test.application.remote_data.api.HotelService
import com.test.application.remote_data.repository.HotelRepositoryInterface

class HotelRepository(
    private val hotelService: HotelService
) : HotelRepositoryInterface<Hotel> {

    init {
        Log.d("@@@", "HotelRepository created with hotelService: $hotelService")
    }
    override suspend fun getData(): Hotel {
        val hotelDTO = hotelService.getHotelInfo()
        Log.d("@@@", "Repository Hotel DTO: $hotelDTO")
        val hotel = mapHotelDTOToDomain(hotelDTO)
        Log.d("@@@", "Repository Mapped Hotel: $hotel")
        return hotel
    }
}