package com.test.application.core.interactor

import com.test.application.core.domain.Hotel
import com.test.application.core.utilities.AppState

interface HomeScreenInteractor : Interactor<AppState> {
    suspend fun getHotelInfo(): Hotel
}