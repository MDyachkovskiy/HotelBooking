package com.test.application.booking

import com.test.application.core.interactor.BookingScreenInteractor
import com.test.application.core.utilities.AppState
import com.test.application.core.view.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BookingViewModel(
    private val interactor: BookingScreenInteractor
) : BaseViewModel<AppState>() {
    private val _stateFlow = MutableStateFlow<AppState>(AppState.Loading)
    val stateFlow: StateFlow<AppState> get() = _stateFlow

    fun loadBookingInfo() {
        viewModelCoroutineScope.launch {
            _stateFlow.value = AppState.Loading
            try {
                val hotelInfo = interactor.getBookingInfo()
                _stateFlow.emit(AppState.Success(hotelInfo))
            } catch (e:Throwable) {
                _stateFlow.emit(AppState.Error(e))
            }
        }
    }
}