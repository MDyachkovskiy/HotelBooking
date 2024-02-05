package com.test.application.hotelbooking.di

import com.test.application.booking.BookingViewModel
import com.test.application.core.domain.Booking
import com.test.application.core.domain.Hotel
import com.test.application.core.domain.Room
import com.test.application.core.interactor.BookingScreenInteractor
import com.test.application.core.interactor.BookingScreenInteractorImpl
import com.test.application.core.interactor.HomeScreenInteractor
import com.test.application.core.interactor.HomeScreenInteractorImpl
import com.test.application.core.interactor.RoomListInteractor
import com.test.application.core.interactor.RoomListInteractorImpl
import com.test.application.remote_data.repository.BookingRepositoryImpl
import com.test.application.home.HomeViewModel
import com.test.application.remote_data.repository.HotelRepositoryImpl
import com.test.application.remote_data.repository.RoomListRepositoryImpl
import com.test.application.hotelbooking.BuildConfig
import com.test.application.remote_data.api.BookingService
import com.test.application.remote_data.api.HotelService
import com.test.application.remote_data.api.RoomListService
import com.test.application.core.repository.BookingRepository
import com.test.application.core.repository.HotelRepository
import com.test.application.core.repository.RoomListRepository
import com.test.application.room_list.view.RoomListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single {
        get<Retrofit>().create(HotelService::class.java)
    }

    single {
        get<Retrofit>().create(RoomListService::class.java)
    }

    single {
        get<Retrofit>().create(BookingService::class.java)
    }
}

val repositoryModule = module {
    single<HotelRepository<Hotel>> { HotelRepositoryImpl(hotelService = get()) }
    single<RoomListRepository<List<Room>>> { RoomListRepositoryImpl(roomsService = get()) }
    single<BookingRepository<Booking>> { BookingRepositoryImpl(bookingService = get()) }
}

val interactorModule = module {
    single<HomeScreenInteractor> {
        HomeScreenInteractorImpl(repositoryRemote = get())
    }

    single<RoomListInteractor> {
        RoomListInteractorImpl(repository = get())
    }

    single<BookingScreenInteractor> {
       BookingScreenInteractorImpl(repositoryRemote = get())
    }
}

val viewModelModule = module {
    viewModel { HomeViewModel(interactor = get()) }
    viewModel { RoomListViewModel(interactor = get()) }
    viewModel { BookingViewModel(interactor = get()) }
}