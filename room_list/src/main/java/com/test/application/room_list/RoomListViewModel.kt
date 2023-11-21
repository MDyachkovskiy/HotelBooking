package com.test.application.room_list

import com.test.application.core.interactor.RoomListInteractor
import com.test.application.core.utilities.AppState
import com.test.application.core.view.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RoomListViewModel(
    private val interactor: RoomListInteractor
) : BaseViewModel<AppState>() {

    private val _stateFlow = MutableStateFlow<AppState>(AppState.Loading)
    val stateFlow: StateFlow<AppState> get() = _stateFlow

    fun loadRoomsInfo() {
        viewModelCoroutineScope.launch {
            _stateFlow.value = AppState.Loading
            try {
                val roomsInfo = interactor.getRoomsInfo()
                _stateFlow.emit(AppState.Success(roomsInfo))
            } catch (e:Throwable) {
                _stateFlow.emit(AppState.Error(e))
            }
        }
    }
}