package com.test.application.core.interactor

import com.test.application.core.domain.Room
import com.test.application.core.utilities.AppState

interface RoomListInteractor: Interactor<AppState> {
    suspend fun getRoomsInfo(): List<Room>
}