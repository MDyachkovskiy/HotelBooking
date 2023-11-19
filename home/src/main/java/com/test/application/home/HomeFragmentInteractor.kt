package com.test.application.home

import com.test.application.core.domain.Hotel
import com.test.application.core.utilities.AppState
import com.test.application.core.view.Interactor
import com.test.application.remote_data.Repository

class HomeFragmentInteractor(
    private val repositoryRemote: Repository<Hotel>
) : Interactor<AppState> {
    override suspend fun getData(): AppState {
        return AppState.Success(repositoryRemote.getData())
    }
}