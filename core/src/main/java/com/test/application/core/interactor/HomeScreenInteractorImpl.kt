package com.test.application.core.interactor

import com.test.application.core.domain.Hotel
import com.test.application.remote_data.repository.HotelRepositoryInterface

class HomeScreenInteractorImpl(
    private val repositoryRemote: HotelRepositoryInterface<Hotel>
) : HomeScreenInteractor {
    override suspend fun getHotelInfo(): Hotel {
        val hotel = repositoryRemote.getData()
        return hotel
    }
}