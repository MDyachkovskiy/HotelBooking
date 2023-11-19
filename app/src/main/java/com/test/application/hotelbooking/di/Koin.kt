package com.test.application.hotelbooking.di

import com.test.application.core.domain.Hotel
import com.test.application.core.domain.Room
import com.test.application.core.interactor.HomeScreenInteractor
import com.test.application.core.interactor.HomeScreenInteractorImpl
import com.test.application.core.interactor.RoomListInteractor
import com.test.application.core.interactor.RoomListInteractorImpl
import com.test.application.home.HomeViewModel
import com.test.application.core.repository.HotelRepository
import com.test.application.core.repository.RoomListRepository
import com.test.application.remote_data.api.HotelService
import com.test.application.remote_data.api.RoomListService
import com.test.application.remote_data.repository.Repository
import com.test.application.room_list.RoomListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single {
        Retrofit.Builder()
            .baseUrl("https://run.mocky.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single {
        get<Retrofit>().create(HotelService::class.java)
    }

    single {
        get<Retrofit>().create(RoomListService::class.java)
    }
}

val repositoryModule = module {
    single<Repository<Hotel>> { HotelRepository(hotelService = get()) }
    single<Repository<List<Room>>> {RoomListRepository(roomsService = get())}
}

val interactorModule = module {
    single<HomeScreenInteractor> {
        HomeScreenInteractorImpl(repositoryRemote = get())
    }

    single<RoomListInteractor> {
        RoomListInteractorImpl(repository = get())
    }
}

val viewModelModule = module {
    viewModel { HomeViewModel(interactor = get()) }
    viewModel { RoomListViewModel(interactor = get()) }
}