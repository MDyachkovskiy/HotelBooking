package com.test.application.core.interactor

import com.test.application.core.domain.Hotel
import com.test.application.remote_data.repository.HotelRepository

class HomeScreenInteractorImpl(
    private val repositoryRemote: HotelRepository<Hotel>
) : HomeScreenInteractor {
    override suspend fun getHotelInfo(): Hotel {
        val hotel = repositoryRemote.getData()
        return hotel
    }
}