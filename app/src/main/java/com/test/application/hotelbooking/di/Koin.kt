package com.test.application.hotelbooking.di

import com.test.application.core.domain.Hotel
import com.test.application.core.interactor.HomeScreenInteractor
import com.test.application.core.interactor.HomeScreenInteractorImpl
import com.test.application.home.HomeViewModel
import com.test.application.core.repository.HotelRepository
import com.test.application.remote_data.api.HotelService
import com.test.application.remote_data.repository.Repository
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
}

val repositoryModule = module {
    single<Repository<Hotel>> { HotelRepository(hotelService = get()) }
}

val interactorModule = module {
    single<HomeScreenInteractor> {
        HomeScreenInteractorImpl(repositoryRemote = get())
    }
}

val viewModelModule = module {
    viewModel {
        HomeViewModel(interactor = get())
    }
}