package com.test.application.core.mapper

import com.test.application.core.domain.AboutTheHotel
import com.test.application.core.domain.Hotel
import com.test.application.core.domain.Room
import com.test.application.remote_data.dto.HotelDTO
import com.test.application.remote_data.dto.RoomDTO

fun mapHotelDTOToDomain(hotelDTO: HotelDTO): Hotel {
    return Hotel (
        id = hotelDTO.id,
        name = hotelDTO.name,
        address = hotelDTO.adress,
        minimalPrice = hotelDTO.minimal_price,
        priceForIt = hotelDTO.price_for_it,
        rating = hotelDTO.rating,
        ratingName = hotelDTO.rating_name,
        imageUrls = hotelDTO.image_urls,
        aboutTheHotel = AboutTheHotel(
            description = hotelDTO.about_the_hotel.description,
            peculiarities = hotelDTO.about_the_hotel.peculiarities
        )
    )
}

fun mapRoomDTOToRoom(roomDTO: RoomDTO): Room {
    return Room(
        id = roomDTO.id,
        name = roomDTO.name,
        price = roomDTO.price,
        pricePer = roomDTO.price_per,
        peculiarities = roomDTO.peculiarities,
        imageUrls = roomDTO.image_urls
    )
}