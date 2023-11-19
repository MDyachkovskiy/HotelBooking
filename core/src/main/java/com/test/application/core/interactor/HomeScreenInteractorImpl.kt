package com.test.application.core.interactor

import com.test.application.core.domain.Hotel
import com.test.application.core.utilities.AppState
import com.test.application.remote_data.repository.Repository

class HomeScreenInteractorImpl(
    private val repositoryRemote: Repository<Hotel>
) : HomeScreenInteractor {
    override suspend fun getHotelInfo(): Hotel {
        return repositoryRemote.getData()
    }

    override suspend fun getData(): AppState {
        return AppState.Success(getHotelInfo())
    }
}