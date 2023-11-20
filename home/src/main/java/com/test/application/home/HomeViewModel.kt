package com.test.application.home

import android.util.Log
import com.test.application.core.interactor.HomeScreenInteractor
import com.test.application.core.utilities.AppState
import com.test.application.core.view.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val interactor: HomeScreenInteractor
) : BaseViewModel<AppState> () {

    private val _stateFlow = MutableStateFlow<AppState>(AppState.Loading)
    val stateFlow: StateFlow<AppState> get() = _stateFlow

    fun loadHotelInfo() {
        viewModelCoroutineScope.launch {
            _stateFlow.value = AppState.Loading
            try {
                val hotelInfo = interactor.getHotelInfo()
                Log.d("@@@", "Hotel info loaded: $hotelInfo")
                _stateFlow.emit(AppState.Success(hotelInfo))
            } catch (e:Throwable) {
                Log.e("@@@", "Error loading hotel info", e)
                _stateFlow.emit(AppState.Error(e))
            }
        }
    }
}